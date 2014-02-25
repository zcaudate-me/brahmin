

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