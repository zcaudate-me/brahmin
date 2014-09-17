(ns brahmin.category.monad
  (:require
   [purnam.native.functions :refer [js-mapcat obj-only]]
   [brahmin.common :refer [get-context]]
   [brahmin.category.functor :refer [fmap-map-r]]
   [brahmin.protocols :refer [Monad bind join fmap op]])
  (:use-macros [brahmin.macros :only [extend-all with-context]]))

(defn with-current-context
  "Creates a function that applies function f with the supplied arguments,
   while providing the currently valid dynamic scoped monadic context
   inside f's lexical scope."
  [f]
  (let [current-context (get-context)]
    (fn [& args]
      (with-context current-context
        (apply f args)))))

(defn bind-default
  ([mv g]
     (join (fmap mv g)))
  ([mv g mvs]
     (join (fmap mv g mvs))))

(defn join-function [mv] mv)

(defn join-atom [mv] mv)

(defn join-array [mv]
  (let [output (array)]
    (.map mv (fn [e]
               (if (coll? e)
                 (doseq [i e] (.push output i))
                 (.push output e))))
    output))

(defn bind-array
  ([mv g]
     (js-mapcat g mv))
  ([mv g mvs]
     (apply js-mapcat g mv mvs)))

(defn bind-coll
  ([mv g]
     (into (empty mv)
           (mapcat g mv)))
  ([mv g mvs]
     (into (empty mv)
           (apply mapcat g mv mvs))))

(defn bind-list
 ([c g]
    (with-meta
      (apply list (mapcat g c))
      (meta c)))
 ([c g ss]
    (with-meta
      (apply list (apply mapcat g c ss))
      (meta c))))

(defn bind-seq
  ([c g]
    (with-meta
      (mapcat g c)
      (meta c)))
  ([c g ss]
    (with-meta
      (apply mapcat g c ss)
      (meta c))))

(defn bind-lazyseq
  ([c g]
     (bind-seq c (with-current-context g)))
  ([c g ss]
     (bind-seq c (with-current-context g) ss)))

(defn join-step [mv e]
  (if (coll? e)
    (op mv e)
    (conj mv e)))

(defn join-seq [mv]
  (with-meta
    (reduce join-step [] mv)
    (meta mv)))

(defn join-coll [mv]
  (into (empty mv) (join-seq mv)))

(defn join-list [c]
  (with-meta
    (apply list (join-seq c))
    (meta c)))

(defn join-map-r [m]
  (mapcat (fn [[k x :as e]]
              (if (map? x)
                (map (fn [[kx vx]]
                         [(if (and k kx)
                            (->> (flatten [k kx])
                                 (map name)
                                 (clojure.string/join "/")
                                 keyword)
                            (or k kx))
                          vx])
                       x)
                [e]))
            m))

(defn join-map [m]
  (into (empty m) (join-map-r m)))

(defn bind-map
  ([m g]
     (into (empty m)
           (join-map-r
            (fmap-map-r m g))))
  ([m g ms]
     (into (empty m)
           (join-map-r
            (fmap-map-r m g ms)))))

(defn join-object-r [m]
  (mapcat (fn [[k x :as e]]
              (if (map? x)
                (map (fn [[kx vx]]
                         [(if (and k kx)
                            (clojure.string/join "/" (flatten [k kx]))
                            (or k kx))
                          vx])
                       x)
                [e]))
            m))

(defn join-object [m]
  (obj-only m :join)
  (apply conj (js-obj) (join-object-r m)))

(defn bind-object
  ([m g]
     (obj-only m :bind)
     (apply conj (js-obj)
           (join-object-r
            (fmap-map-r m g))))
  ([m g ms]
     (mapv #(obj-only % :bind) (conj ms m))
     (apply conj (js-obj)
           (join-object-r
            (fmap-map-r m g ms)))))

(extend-type nil  Monad
  (bind
    ([_ _] nil)
    ([_ _ _] nil))
  (join [_] nil))

(extend-all Monad
 [(bind ([mv g] (?% mv g))
        ([mv g mvs] (?% mv g mvs)))
  (join [mv] (?% mv))]

 object            [bind-object join-object]
 array             [bind-array join-array]
 Atom              [bind-default join-atom]
 function          [bind-default join-function]

 LazySeq           [bind-lazyseq join-seq]
 [EmptyList
  IndexedSeq RSeq NodeSeq
  ArrayNodeSeq List Cons
  ChunkedCons ChunkedSeq
  KeySeq ValSeq Range
  PersistentArrayMapSeq] [bind-list join-list]

 [PersistentVector
  Subvec BlackNode
  RedNode

  PersistentHashSet
  PersistentTreeSet] [bind-coll join-coll]

 [PersistentHashMap
  PersistentTreeMap
  PersistentArrayMap]  [bind-map join-map])
