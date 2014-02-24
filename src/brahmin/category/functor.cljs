(ns brahmin.category.functor
  (:require
    [purnam.native.functions :refer [js-map js-type obj-only]]
    [brahmin.protocols :refer [Functor]])
  (:use-macros
   [brahmin.macros :only [extend-all]]))

(defn fmap-function
  ([fv g] (comp g fv))
  ([fv g fvs] (apply comp g fv fvs)))

(defn fmap-array
    ([fv g] (js-map g fv))
    ([fv g fvs] (apply js-map g fv fvs)))

(defn fmap-string
  ([fv g] (apply str (g fv)))
  ([fv g fvs] (apply str (apply g fv fvs))))

(defn fmap-keyword
  ([fv g] (keyword (fmap-string (name fv) g)))
  ([fv g fvs] (keyword (fmap-string (name fv) g
                                    (map name fvs)))))

(defn fmap-atom
    ([fv g] (do (swap! fv g) fv))
    ([fv g fvs] (do (apply swap! fv g (map deref fvs)) fv)))

(defn fmap-list
  ([fv g] (with-meta (apply list (map g fv)) (meta fv)))
  ([fv g fvs] (with-meta (apply list (apply map g fv fvs)) (meta fv))))

(defn fmap-lazyseq
  ([fv g] (with-meta (map g fv) (meta fv)))
  ([fv g fvs] (with-meta (apply map g fv fvs) (meta fv))))

(defn fmap-coll
  ([fv g] (with-meta (into (empty fv) (map g fv)) (meta fv)))
  ([fv g fvs] (with-meta
      (into (empty fv) (apply map g fv fvs)) (meta fv))))

(defn group-entries [k ms]
  (map val
         (remove nil?
                   (map #(find % k) ms))))

(defn apply-key [g maps k]
  [k (apply g (into [] (group-entries k maps)))])

(defn fmap-map-r
  ([m g]
     (map (fn [[k v]] [k (g v)]) m))
  ([m g ms]
     (let [source (cons m ms)
           keys (distinct (into [] (flatten (map keys source))))]
       (map (partial apply-key g source) keys))))

(defn fmap-map
  ([fv g]
    (with-meta
     (into (empty fv) (fmap-map-r fv g))
     (meta fv)))
  ([fv g fvs]
    (with-meta
     (into (empty fv) (fmap-map-r fv g fvs))
     (meta fv))))

(defn fmap-object
  ([fv g]
     (obj-only fv :fmap)
     (apply conj (js-obj) (fmap-map-r fv g)))
  ([fv g fvs]
     (obj-only fv :fmap)
     (apply conj (js-obj) (fmap-map-r fv g fvs))))

(extend-type nil Functor
 (fmap
   ([_ _] nil)
   ([_ _ _] nil)))

(extend-type EmptyList Functor
 (fmap
   ([_ _] ())
   ([_ _ _] ())))

(extend-all Functor
  [(fmap
    ([fv g] (?% fv g))
    ([fv g fvs] (?% fv g fvs)))]
    
  object            [fmap-object]
  function          [fmap-function]
  array             [fmap-array]
  string            [fmap-string]
  Keyword           [fmap-keyword]
  Atom              [fmap-atom]

  [IndexedSeq RSeq NodeSeq
   ArrayNodeSeq List Cons
   ChunkedCons ChunkedSeq
   KeySeq ValSeq Range
   PersistentArrayMapSeq]    [fmap-list]

  LazySeq                    [fmap-lazyseq]

  [PersistentVector
   Subvec BlackNode
   RedNode
   PersistentHashSet
   PersistentTreeSet]        [fmap-coll]

  [PersistentHashMap
   PersistentTreeMap
   PersistentArrayMap]       [fmap-map])
