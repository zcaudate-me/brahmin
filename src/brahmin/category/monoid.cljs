(ns brahmin.category.monoid
  (:require
    [purnam.native.functions :refer [obj-only]]
    [brahmin.protocols :refer [Monoid id]])
  (:use-macros [brahmin.macros :only [extend-all]]))

(extend-type nil Monoid
  (id [_] nil))

(extend-all Monoid
 [(id [m] ?%)]

 object            [(do (obj-only m :id) (js-obj))]
 array             [(array)]
 function          [identity]
 string            [""]
 number            [0]
 Keyword           [(keyword "")]
 Atom              [(atom (id (deref m)))]

 LazySeq           [(lazy-seq [])]

 [EmptyList
  IndexedSeq RSeq NodeSeq
  ArrayNodeSeq List Cons
  ChunkedCons ChunkedSeq
  KeySeq ValSeq Range
  PersistentArrayMapSeq
  EmptyList]       [(list)]

 [PersistentVector
  Subvec BlackNode
  RedNode]         [(vector)]

 [PersistentHashSet
  PersistentTreeSet] [(hash-set)]

 [PersistentHashMap
  PersistentTreeMap
  PersistentArrayMap]  [(hash-map)])
