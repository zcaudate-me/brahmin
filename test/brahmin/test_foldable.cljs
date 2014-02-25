(ns brahmin.test-foldable
  (:use [brahmin.core :only [fold foldmap]])
  (:use-macros [purnam.core :only [obj arr !]]
               [purnam.test :only [fact facts]]))

(fact
 "Data structures: fold."

 (fold [1 2 3 4 5 6]) => 21

 (fold (list "a" "b" "c")) => "abc"

 (fold (seq [:a :b :c])) => :abc

 (fold (lazy-seq [[1] (list 2) (seq [3])])) => [1 2 3]

 (fold #{1 2 3}) => 6

 (fold {:a 1 :b 2 :c 3}) => 6
 
 (fold (array 1 2 3)) => 6 

 (fold (obj :a 1 :b 2 :c 3)) => 6)