(ns brahmin.core)

(defn parse-bindings
  ([vs] (parse-bindings (drop 2 vs) [(first vs)] [(second vs)]))
  ([vs syms bnds]
     (cond (= '| (first vs))
           (recur (drop 3 vs)
                  (conj syms (second vs))
                  (conj bnds (nth vs 2)))

           :else [vs syms bnds])))

(defmacro do> [bindings body]
  (if (seq bindings)
    (let [[more syms bnds] (parse-bindings bindings)]
      (concat
       ['purnam.core/bind]
       bnds
       [`(fn [~@syms]
           (do> [~@more] ~body))]))
    (list 'purnam.core/return body)))

(defmacro $>
  ([f x] `(~f ~x))
  ([f x & more]
   `($> (~f ~x) ~@more)))