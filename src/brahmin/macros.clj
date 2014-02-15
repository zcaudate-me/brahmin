(ns brahmin.macros
  (:require [clojure.walk :as w]))

(defn extend-single [proto ptmpls t funcs]
  (apply list `extend-type t proto
    (map (fn [tmpl f] (w/prewalk-replace {'?% f} tmpl))
       ptmpls funcs)))

(defn extend-entry [proto ptmpls [ts funcs]]
  (cond (vector? ts)
        (map #(extend-single proto ptmpls % funcs) ts)

        :else
        [(extend-single proto ptmpls ts funcs)]))

(defmacro extend-all [proto ptmpls & args]
  (let [types (partition 2 args)]
    `(do
       ~@(mapcat #(extend-entry proto ptmpls %) types))))

(defmacro with-context
 "Establishes the monadic context that can be accessed
  with the get-context function in the dynamic scope inside
  the body."
 [context & body]
 `(binding [brahmin.common/*pure-context* ~context]
    ~@body))