(ns brahmin.category.applicative
  (:require
    [purnam.native.functions :refer [js-map js-mapcat js-merge obj-only]]
    [brahmin.category.functor :refer [group-entries]]
    [brahmin.protocols :refer [Applicative fmap]])
  (:use-macros [brahmin.macros :only [extend-all]]))

(defn fapply-array
  ([ag av]
    (js-mapcat #(js-map % av) ag))
  ([ag av avs]
    (js-mapcat #(apply js-map % av avs) ag)))

(defn fapply-atom
  ([ag av]
     (fmap av (deref ag)))
  ([ag av avs]
     (fmap av (deref ag) avs)))

(defn pure-coll [av v]
  (conj (empty av) v))

(defn fapply-coll
  ([ag av]
     (into (empty av)
           (mapcat #(map % av) ag)))
  ([ag av avs]
     (into (empty av)
           (mapcat #(apply map % av avs) ag))))

(defn fapply-list
 ([ag av]
    (apply list
          (mapcat #(map % av) ag)))
 ([ag av avs]
    (apply list
          (mapcat #(apply map % av avs) ag))))

(defn fapply-lazyseq
 ([ag av]
    (mapcat #(map % av) ag))
 ([ag av avs]
    (mapcat #(apply map % av avs) ag)))
    
(defn fapply-map
  ([ag av]
     (into
      (if-let [f (ag nil)]
        (fmap av f)
        av)
      (remove
       nil?
       (map (fn [[kg vg]]
                (if-let [[kv vv] (find av kg)]
                  [kv (vg vv)]))
              ag))))
  ([ag av avs]
     (into
      (if-let [f (ag nil)]
        (fmap av f avs)
        (apply merge av avs))
      (remove
       nil?
       (map (fn [[kg vg]]
                (if-let [vs (seq (into [] (group-entries
                                           kg (cons av avs))))]
                  [kg (apply vg vs)]))
              ag)))))
              
(defn pure-object [av v]
  (obj-only av :pure)
  (let [o (js-obj)]
    (aset o "" v)
    o))

(defn fapply-object
  ([ag av]
     (mapv #(obj-only % :fapply) [ag av])
     (apply conj
      (if-let [f (get ag nil)]
        (fmap av f)
        av)
      (remove
       nil?
       (map (fn [[kg vg]]
                (if-let [[kv vv] (find av kg)]
                  [kv (vg vv)]))
              ag))))
  ([ag av avs]
     (mapv #(obj-only % :fapply) (conj avs ag av))
     (apply conj
      (if-let [f (get ag nil)]
        (fmap av f avs)
        (js-merge av avs))
      (remove
       nil?
       (map (fn [[kg vg]]
                (if-let [vs (seq (into [] (group-entries
                                           kg (cons av avs))))]
                  [kg (apply vg vs)]))
            ag)))))

(extend-type nil Applicative
  (pure [_ _] nil)
  (fapply
    ([_ _] nil)
    ([_ _ _] nil)))

(extend-all Applicative
  [(pure [av v] ?%)
   (fapply
      ([ag av] (?% ag av))
      ([ag av avs] (?% ag av avs)))]

  object            [(pure-object av v) fapply-object]
  array             [(array v) fapply-array]
  Atom              [(atom v) fapply-atom]
  LazySeq           [(lazy-seq [v]) fapply-lazyseq]

  [IndexedSeq RSeq NodeSeq
   ArrayNodeSeq List Cons
   ChunkedCons ChunkedSeq
   KeySeq ValSeq Range
   PersistentArrayMapSeq
   EmptyList]          [(list v) fapply-list]

  [PersistentVector
   Subvec BlackNode
   RedNode]            [(vector v) fapply-coll]

  [PersistentHashSet
   PersistentTreeSet]  [(hash-set v) fapply-coll]


  [PersistentHashMap
   PersistentTreeMap
   PersistentArrayMap]  [(hash-map nil v) fapply-map])