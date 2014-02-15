(ns purnam.category
  (:require [purnam.native.functions :refer [js-map]]
            [purnam.category.common :refer [get-context]]
            [purnam.category.functor]
            [purnam.category.applicative]
            [purnam.category.magma]
            [purnam.category.monoid]
            [purnam.category.foldable]
            [purnam.category.monad]
            [purnam.category.curried]
            [purnam.category.maybe]
            [purnam.category.protocols :as p])
  (:use-macros [purnam.category.macros :only [with-context]]))

(defn fmap
  ([f]
    (if (= identity f) f
        (fn [functor & more]
          (apply fmap f functor more))))
  ([f functor]
    (p/fmap functor f))
  ([f functor & more]
    (p/fmap functor f more)))

(defn pure
  ([applicative]
     #(p/pure applicative %))
  ([applicative x]
     (p/pure applicative x)))

(defn fapply
  ([af]
    (fn [av & avs]
      (apply fapply af av avs)))
  ([af av]
    (p/fapply af av))
  ([af av & avs]
    (p/fapply af av avs)))

(defn op
  ([x y]
     (p/op x y))
  ([x y & ys]
     (p/op x y ys)))

(defn id
 [x]
 (p/id x))

(defn fold
 [fd]
 (p/fold fd))

(defn foldmap
 ([f]
    (fn [fd]
      (p/foldmap fd f)))
 ([f fd]
    (p/foldmap fd f)))

(defn <*>
  ([af]
     (fn [a & as]
       (apply <*> af a as)))
  ([af av]
     (p/fapply af av))
  ([af av & avs]
     (reduce p/fapply af (cons av avs))))

(defn join
  [monadic]
  (p/join monadic))

(defn return
  [x]
  (p/pure (get-context) x))

(def unit return)

(defn bind
  ([f]
     (fn [monadic & ms]
       (apply bind monadic f ms)))
  ([monadic f]
     (with-context monadic
       (p/bind monadic f)))
  ([monadic monadic2 & args]
     (with-context monadic
       (p/bind monadic (last args)
               (cons monadic2 (butlast args))))))

(defn >>=
  ([monadic]
    (fn [f & fs]
      (apply >>= monadic f fs) ))
  ([monadic f]
    (bind monadic f))
  ([monadic f & fs]
    (reduce bind monadic (cons f fs))))

(defn >=>
  ([f]
     (fn [g & gs]
       (apply >=> f g gs)))
  ([f g]
     (fn
       ([x]
          (bind (f x) g))
       ([x & xs]
          (bind (apply f x xs) g))))
  ([f g & hs]
     (fn
       ([x]
          (apply >>= (f x) g hs))
       ([x & xs]
          (apply >>= (apply f x xs) g hs)))))

(defn <=<
  ([f]
     (fn [g & gs]
       (apply <=< f g gs)))
  ([f g]
     (>=> g f))
  ([f g & hs]
     (apply >=> (reverse (into [f g] hs)))))

(defn guard [b]
  (if b (return []) (id (get-context))))
       
(def curry purnam.category.curried/curry)

(def arities purnam.category.curried/arities)

(defn just [n]
  (purnam.category.maybe/Just. n))
