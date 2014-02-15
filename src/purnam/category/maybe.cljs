(ns purnam.category.maybe
  (:require [purnam.category.protocols :refer [Curried Functor Applicative Monad Monoid Magma Foldable id fmap fold bind op]]))

(deftype Just [v]
  Object
  (hashCode [_] (hash v))
  (equals [this that]
    (or (identical? this that)
        (and (instance? Just that)
             (= v (fold that)))))
             
  Functor
  (fmap [_ g]
    (Just. (g v)))
  (fmap [_ g jvs]
    (if (some nil? jvs)
      nil
      (Just. (apply g v (map fold jvs)))))
  
  Applicative
  (pure [_ x]
    (Just. x))
  (fapply [_ jv]
    (fmap jv v))
  (fapply [_ jv jvs]
    (fmap jv v jvs))
    
  Monad
  (bind [_ g]
    (g v))
  (bind [_ g jvs]
    (if (some nil? jvs)
      nil
      (apply g v (map fold jvs))))
  (join [jjv]
    (if (or (nil? v) (instance? Just v)) v jjv))
    
  Foldable
  (fold [_] v)
  (foldmap [_ g] (g v))
  
  Magma
  (op [x y]
    (if-not (nil? y)
      (Just. (op v (fold y)))
      x))
  (op [x y ys]
    (if-let [ys* (map fold (remove nil? (cons y ys)))]
      (Just. (op v (first ys*) (rest ys*)))
      x))
      
  Monoid
  (id [_] nil))
