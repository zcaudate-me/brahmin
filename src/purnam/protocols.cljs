(ns brahmin.protocols)

(defprotocol Curried
  (arity [f]))
  
(defprotocol Functor
  (fmap [fv g]
        [fv g fvs]))
        
(defprotocol Applicative
  (pure [av v])
  (fapply [ag av] [ag av avs]))

(defprotocol Monad
  (bind [mv g] [mv g mvs])
  (join [mv]))

(defprotocol Magma
  (op [x y] [x y ys]))

(defprotocol Monoid
  (id [m]))

(defprotocol Foldable
  (fold [fd])
  (foldmap [fd g]))