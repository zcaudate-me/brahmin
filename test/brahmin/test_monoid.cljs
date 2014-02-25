(ns brahmin.test-monoid
  (:require [brahmin.core :refer [id]]
            [purnam.test])
  (:require-macros [purnam.core :refer [obj arr !]]
                   [purnam.test :refer [fact facts]]))

(facts
 "Data structures: id."

 (id [2]) => []

 (id (list 4 5 6)) => (list)

 (id (seq [1 2])) => (empty (seq [2]))

 (id (lazy-seq [1 23])) => (lazy-seq [])

 (id #{2 3}) => #{}

 (id {:1 2}) => {}
 
 (id (array 1 2 3 4)) => (array)
 
 (id (obj :a 1)) => (obj))