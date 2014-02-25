(ns brahmin.test-magma
  (:require [brahmin.core :refer [op]]
            [purnam.test])
  (:require-macros [purnam.core :refer [obj arr !]]
                   [purnam.test :refer [fact facts]]))


(fact
  (op nil 1) => 1  
  
  (op "a" "b" "c") => "abc"
  
  (op (arr 1 2 3) [4 5 6]) => (arr 1 2 3 4 5 6)
  
  (op (arr 1 2 3) (arr 4 5 6)) => (arr 1 2 3 4 5 6)
  
  (op [1 2 3] [4 5 6]) => [1 2 3 4 5 6]

  (op [1 2 3] [4 5 6] [7 8 9] [10 11 12])
  => [1 2 3 4 5 6 7 8 9 10 11 12]

  (op (list 1 2 3) (list 4 5 6)) => (list 1 2 3 4 5 6)

  (op (list 1 2 3) (list 4 5 6) (list 7 8 9) (list 10 11 12))
  => (list 1 2 3 4 5 6 7 8 9 10 11 12)

  (op (lazy-seq [1 2 3]) (lazy-seq [4 5 6]))
  => (lazy-seq [1 2 3 4 5 6])

  (op (lazy-seq [1 2 3]) (lazy-seq [4 5 6])
      (lazy-seq [7 8 9]) (lazy-seq [10 11 12]))
  => (lazy-seq [1 2 3 4 5 6 7 8 9 10 11 12])

  (op (seq [1 2 3]) (seq [4 5 6])) => (seq [1 2 3 4 5 6])

  (op (seq [1 2 3]) (seq [4 5 6])
      (seq [7 8 9]) (seq [10 11 12]))
  => (seq [1 2 3 4 5 6 7 8 9 10 11 12])

  (op #{1 2 3 6} #{4 5 6}) => #{1 2 3 4 5 6}

  (op #{1 2 3 6} #{4 5 6} #{7 8 9} #{10 11 12})
  => #{1 2 3 4 5 6 7 8 9 10 11 12}

  (op {:a 1 :b 2} {:a 3 :c 4}) => {:a 3 :b 2 :c 4}

  (op {:a 1 :b 2} {:a 3 :c 4} {:d 5} {:e 6})
  => {:a 3 :b 2 :c 4 :d 5 :e 6}
  
  (op (array 1 2 3) (array 4 5 6)) => (array 1 2 3 4 5 6)
  
  (op (obj :a 1 :b 2) (obj :c 3 :d 4)) => (obj :a 1 :b 2 :c 3 :d 4)) 
