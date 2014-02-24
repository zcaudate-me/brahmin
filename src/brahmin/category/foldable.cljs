(ns brahmin.category.foldable
  (:require
   [purnam.native.functions :refer [js-map obj-only]]
   [brahmin.protocols :refer [Foldable fold foldmap op id]])
  (:use-macros [brahmin.macros :only [extend-all]]))

(defn fold-array [fd] (reduce op fd))

(defn foldmap-array [fd g]
  (fold (js-map g fd)))

(defn fold-atom [fd] (deref fd))

(defn foldmap-atom [fd g] (g (deref fd)))

(defn fold-coll [fd]
  (let [ide (id (first fd))]
    (reduce op ide fd)))

(defn foldmap-coll [fd g]
  (fold (map g fd)))

(defn fold-map [fd]
  (fold-coll (vals fd)))

(defn foldmap-map [fd g]
  (foldmap-coll (seq fd) g))

(defn fold-object [fd]
  (obj-only fd :fold)
  (fold-coll (vals fd)))

(defn foldmap-object [fd g]
  (obj-only fd :fold)
  (foldmap-coll (seq fd) g))

(extend-type nil Foldable
  (fold [_] nil)
  (foldmap [_ _] nil))

(extend-all Foldable
 [(fold [fd] (?% fd))
  (foldmap [fd g] (?% fd g))]

 object            [fold-object foldmap-object]
 array             [fold-array foldmap-array]
 Atom              [fold-atom foldmap-atom]

 [EmptyList LazySeq
  IndexedSeq RSeq NodeSeq
  ArrayNodeSeq List Cons
  ChunkedCons ChunkedSeq
  KeySeq ValSeq Range
  PersistentArrayMapSeq

  PersistentVector
  Subvec BlackNode
  RedNode

  PersistentHashSet
  PersistentTreeSet] [fold-coll foldmap-coll]

 [PersistentHashMap
  PersistentTreeMap
  PersistentArrayMap]  [fold-map foldmap-map])
