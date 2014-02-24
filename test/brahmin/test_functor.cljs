(ns brahmin.test-functor
  (:require [purnam.test]
            [brahmin.core :refer [fmap pure fapply op]])
  (:require-macros [purnam.core :refer [obj arr !]]
                   [purnam.test :refer [fact facts]]))

(facts "Data structures: Functor"

  (fmap inc []) => (vector)

  (fmap inc [1 2 3]) => [2 3 4]

  (fmap + [1 2] [3 4 5] [6 7 8]) => [10 13]

  (fmap inc (list)) => (list)

  (fmap inc (list 1 2 3)) => (list 2 3 4)

  (fmap + (list 1 2) (list 3 4 5) (list 6 7 8))
  => (list 10 13)

  (fmap inc (empty (seq [1]))) => (empty (seq [2]))

  (fmap inc (seq [1 2 3])) => (seq [2 3 4])

  (fmap + (seq [1 2]) (seq [3 4 5]) (seq [6 7 8]))
  => (seq [10 13])

  (fmap inc (lazy-seq [])) => (lazy-seq [])

  (fmap inc (lazy-seq [1 2 3])) => (lazy-seq [2 3 4])

  (fmap + (lazy-seq [1 2])
        (lazy-seq [3 4 5])
        (lazy-seq [6 7 8]))
  => (lazy-seq [10 13])

  (fmap inc {}) => {}

  (fmap inc {:a 1 :b 2 :c 3}) => {:a 2 :b 3 :c 4}

  (fmap + {:a 1 :b 2} {:a 3 :b 4 :c 5} {:a 6 :b 7 :c 8})
  => {:a 10 :b 13 :c 13}
  
  (fmap inc (obj)) => (obj)

  (fmap inc (obj :a 1 :b 2 :c 3)) => (obj :a 2 :b 3 :c 4)

  (fmap + (obj :a 1 :b 2) (obj :a 3 :b 4 :c 5) (obj :a 6 :b 7 :c 8))
  => (obj :a 10 :b 13 :c 13))
  
(fact
  (fmap inc (cons 1 ())) => '(2)
  
  (fmap inc #{1 2 3}) => #{2 3 4}

  (fmap + (arr 1 2 3) (arr 1 2 3) (arr 1 2 3)) => (arr 3 6 9)

  (fmap #(apply str "A" %&) "b" "c" "d") => "Abcd"
  
  (fmap inc {:a 1}) => {:a 2}

  (fmap + {:a 1} {:b 1}) => {:a 1 :b 1}
  
  (fmap inc '(1 2 3)) => '(2 3 4)

  ((fmap str +) 1 2 3) => "6"
  
  @(fmap inc (atom 1)) => 2)

