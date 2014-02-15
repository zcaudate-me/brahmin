(ns purnam.category.magma
  (:require
    [purnam.native.functions :refer [js-concat js-merge]]
    [purnam.native :refer [obj-only]]
    [purnam.category.protocols :refer [Magma op pure]])
  (:use-macros [purnam.category.macros :only [extend-all]]))

(defn op-object
  ([x y]
     (mapv #(obj-only % :op) [x y])
     (js-merge x y))
  ([x y ys]
     (mapv #(obj-only % :op) (conj ys x y))
     (apply js-merge x y ys)))

(defn op-function
  ([x y]
      (cond (= identity x) y
            (= identity y) x
            :else (comp x y)))
  ([x y ys]
    (reduce op-function x (cons y ys))))

(defn op-array
  ([x y]
     (js-concat x y))
  ([x y ys]
     (apply js-concat x y ys)))

(defn op-string
  ([x y]
     (str x y))
  ([x y ys]
     (apply str x y ys)))

(defn op-number
  ([x y]
     (+ x y))
  ([x y ys]
     (apply + x y ys)))

(defn op-keyword
  ([x y]
     (keyword (str (name x) (name y))))
  ([x y ys]
     (keyword (apply str
                     (name x)
                     (name y)
                     (map name ys)))))
(defn op-atom
  ([rx ry]
    (pure rx (op (deref rx) (deref ry))))
  ([rx ry rys]
    (pure rx (op
              (deref rx)
              (deref ry)
              (map deref rys)))))

(defn op-coll
  ([x y]
     (into x y))
  ([x y ys]
     (reduce into (into x y) ys)))

 (defn op-lazyseq
   ([x y]
      (concat x y))
   ([x y ys]
      (apply concat x y ys)))

 (defn op-list
   ([x y]
      (apply list (op-lazyseq x y)))
   ([x y ys]
      (apply list (op-lazyseq x y ys))))


(extend-type nil Magma
  (op
    ([_ y] y)
    ([_ y ys]
       (reduce op y ys))))

(extend-all Magma
 [(op
   ([x y] (?% x y))
   ([x y ys] (?% x y ys)))]

 object             [op-object]
 function          [op-function]
 array             [op-array]
 string            [op-string]
 number            [op-number]
 Keyword           [op-keyword]
 Atom              [op-atom]

 LazySeq           [op-lazyseq]

 [EmptyList
  IndexedSeq RSeq NodeSeq
  ArrayNodeSeq List Cons
  ChunkedCons ChunkedSeq
  KeySeq ValSeq Range
  PersistentArrayMapSeq
  EmptyList]       [op-list]

 [PersistentVector
  Subvec BlackNode
  RedNode

  PersistentHashSet
  PersistentTreeSet

  PersistentHashMap
  PersistentTreeMap
  PersistentArrayMap]  [op-coll])
