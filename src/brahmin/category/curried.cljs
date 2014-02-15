(ns brahmin.category.curried
  (:require
    [gyr.core]
    [brahmin.common]
    [brahmin.protocols :refer [Curried Functor Applicative Monad Monoid Magma Foldable id fmap fold bind]])
  (:use-macros [brahmin.macros :only [with-context]]))
    
(declare curry)

(deftype CFn [n f]
  ICounted
  (-count [_] n)
  
  Curried
  (arity [_] n)

  Functor
  (fmap [_ g]
      (CFn. n (comp g f)))
  (fmap [cf g cfs]
      (reduce fmap (into (list cf g) (seq cfs))))
  
  Monoid
  (id [_] (CFn. 1 identity))
  
  Magma
  (op [x y]
    (cond (= identity f) y
          (= identity (fold y)) x
          :else (fmap x y)))

  Foldable
  (fold [_] f)
  (foldmap [_ g] (g f))
  
  IFn 
  (-invoke [_] 
    (if (> n 0) (curry n f) (f))) 
  (-invoke [_ a0] 
    (if (> n 1) (curry (- n 1) (partial f a0)) (f a0))) 
  (-invoke [_ a0 a1] 
    (if (> n 2) (curry (- n 2) (partial f a0 a1)) (f a0 a1))) 
  (-invoke [_ a0 a1 a2] 
    (if (> n 3) (curry (- n 3) (partial f a0 a1 a2)) (f a0 a1 a2))) 
  (-invoke [_ a0 a1 a2 a3]
    (if (> n 4) (curry (- n 4) (partial f a0 a1 a2 a3)) (f a0 a1 a2 a3))) 
  (-invoke [_ a0 a1 a2 a3 a4] 
    (if (> n 5) (curry (- n 5) (partial f a0 a1 a2 a3 a4)) (f a0 a1 a2 a3 a4))) 
  (-invoke [_ a0 a1 a2 a3 a4 a5] 
    (if (> n 6) (curry (- n 6) (partial f a0 a1 a2 a3 a4 a5)) (f a0 a1 a2 a3 a4 a5))) 
  (-invoke [_ a0 a1 a2 a3 a4 a5 a6] 
    (if (> n 7) (curry (- n 7) (partial f a0 a1 a2 a3 a4 a5 a6)) (f a0 a1 a2 a3 a4 a5 a6))) 
  (-invoke [_ a0 a1 a2 a3 a4 a5 a6 a7] 
    (if (> n 8) (curry (- n 8) (partial f a0 a1 a2 a3 a4 a5 a6 a7)) (f a0 a1 a2 a3 a4 a5 a6 a7))) 
  (-invoke [_ a0 a1 a2 a3 a4 a5 a6 a7 a8] 
    (if (> n 9) (curry (- n 9) (partial f a0 a1 a2 a3 a4 a5 a6 a7 a8)) (f a0 a1 a2 a3 a4 a5 a6 a7 a8))) 
  (-invoke [_ a0 a1 a2 a3 a4 a5 a6 a7 a8 a9] 
    (if (> n 10) (curry (- n 10) (partial f a0 a1 a2 a3 a4 a5 a6 a7 a8 a9)) (f a0 a1 a2 a3 a4 a5 a6 a7 a8 a9))) 
  (-invoke [_ a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10] 
    (if (> n 11) (curry (- n 11) (partial f a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10)) (f a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10))) 
  (-invoke [_ a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11] 
    (if (> n 12) (curry (- n 12) (partial f a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11)) (f a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11))) 
  (-invoke [_ a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12] 
    (if (> n 13) (curry (- n 13) (partial f a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12)) (f a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12))) 
  (-invoke [_ a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13] 
    (if (> n 14) (curry (- n 14) (partial f a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13)) (f a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13))) 
  (-invoke [_ a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14] 
    (if (> n 15) (curry (- n 15) (partial f a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14)) (f a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14))) 
  (-invoke [_ a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15] 
    (if (> n 16) (curry (- n 16) (partial f a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15)) (f a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15)))
  (-invoke [_ a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16] 
    (if (> n 17) (curry (- n 17) (partial f a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16)) (f a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16)))
  (-invoke [_ a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17] 
    (if (> n 18) (curry (- n 18) (partial f a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17)) (f a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17))) 
  (-invoke [_ a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18] 
    (if (> n 19) (curry (- n 19) (partial f a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18)) (f a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18))) 
  (-invoke [_ a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19] 
    (if (> n 20) (curry (- n 20) (partial f a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19)) (f a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19))))
    
(defn arities [f]
 (or (.-cljs$arities f)
     [(.-length f)]))

(defn curry 
  ([f] (curry (apply min (flatten (arities f))) f))
  ([n f] (CFn. n f))
  ([n f & args] (apply (curry n f) args)))
      
(extend-type CFn Applicative
  (pure [_ x]
      (curry 0 (fn [& _] x)))
  
  (fapply [cg cf]
      (curry 1
       (fn
         ([x]
            ((cg x) (cf x)))
         ([x & xs]
            ((apply cg x xs) (apply cf x xs))))))
  (fapply [cg cf hs]
      (curry 1
       (fn
         ([x]
            (apply (cg x) (cf x) (map #(% x) hs)))
         ([x & xs]
            (apply (apply cg x xs) (apply cf x xs)
                   (map #(apply % x xs) hs)))))))

(extend-type CFn Monad
  Monad
  (bind [cf cg]
      (curry 1
       (fn
         ([x]
            (with-context cf
              ((cg (cf x)) x)))
         ([x & xs]
            (with-context cf
              (apply (cg (apply cf x xs)) x xs))))))
  (bind [cf cg hs]
      (curry 1
       (fn
         ([x]
            (with-context cf
              ((apply cg (cf x) (map #(% x) hs)) x)))
         ([x & xs]
            (with-context cf
              (apply (apply cg (apply cf x xs)
                            (map #(apply % x xs) hs))
                     x xs))))))
                     
  (join [cf] (bind cf identity)))