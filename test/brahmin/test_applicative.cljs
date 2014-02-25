(ns brahmin.test-applicative
  (:require [purnam.test]
            [brahmin.core :refer [pure fapply]])
  (:require-macros [purnam.core :refer [obj arr]]
                   [purnam.test :refer [fact facts]]))

 (fact
   @(pure (atom nil) 1) => 1

   (pure #{1 2 3} 1) => #{1}

   (pure [4 5] 1) => [1]

   (pure (list) 1) => (list 1)

   (pure (seq [3]) 1) => (seq [1])

   (pure (lazy-seq [4]) 1) => (lazy-seq [1])

   (pure #{} 1) => #{1}

   (pure {} 1) => {nil 1}
   
   (pure (obj) 1) => (obj nil 1))

 (fact
   (fapply [] []) => []

   (fapply [] [1 2 3]) => []

   (fapply [inc dec] []) => []

   (fapply [inc dec] [1 2 3]) => [2 3 4 0 1 2]

   (fapply [+ *] [1 2 3] [4 5 6]) => [5 7 9 4 10 18]

   (fapply (list) (list)) => (list)

   (fapply (list) (list 1 2 3)) => (list)

   (fapply (list inc dec) (list)) => (list)

   (fapply (list inc dec) (list 1 2 3))
   => (list 2 3 4 0 1 2)

   (fapply (list + *) (list 1 2 3) (list 4 5 6))
   => (list 5 7 9 4 10 18)

   (fapply (empty (seq [2])) (empty (seq [3])))
   => (empty (seq [1]))

   (fapply (empty (seq [33])) (seq [1 2 3]))
   => (empty (seq [44]))

   (fapply (seq [inc dec]) (empty (seq [1])))
   => (empty (seq [3]))

   (fapply (seq [inc dec]) (seq [1 2 3]))
   => (seq [2 3 4 0 1 2])

   (fapply (seq [+ *]) (seq [1 2 3]) (seq [4 5 6]))
   => (seq [5 7 9 4 10 18])

   (fapply (lazy-seq []) (lazy-seq []))
   => (lazy-seq [])

   (fapply (lazy-seq []) (lazy-seq [1 2 3]))
   => (lazy-seq [])

   (fapply (lazy-seq [inc dec]) (lazy-seq []))
   => (lazy-seq [])

   (fapply (lazy-seq [inc dec]) (lazy-seq [1 2 3]))
   => (lazy-seq [2 3 4 0 1 2])

   (fapply (lazy-seq [+ *])
           (lazy-seq [1 2 3])
           (lazy-seq [4 5 6]))
   => (lazy-seq [5 7 9 4 10 18])

   (fapply #{} #{}) => #{}

   (fapply #{} #{1 2 3}) => #{}

   (fapply #{inc dec} #{}) => #{}

   (fapply #{inc dec} #{1 2 3}) => #{2 3 4 0 1}

   (fapply #{+ *} #{1 2 3} #{4 5 6}) => #{5 7 9 4 10 18}

   (fapply {} {}) => {}

   (fapply {} {:a 1 :b 2 :c 3}) => {:a 1 :b 2 :c 3}

   (fapply {:a inc} {}) => {}

   (fapply {:a inc :b dec nil (partial * 10)}
           {:a 1 :b 2 :c 3 :d 4 nil 5})
   => {:a 2 :b 1 :c 30 :d 40 nil 50}

   (fapply {:a + :b * :c /} 
     {:a 1 :c 2} 
     {:a 3 :b 4} 
     {:c 2 :d 5})
   => {:a 4 :b 4 :c 1 :d 5}

   (fapply (arr dec inc #(* 2 %)) (arr 1 2 3 4))
   => (arr 0 1 2 3 2 3 4 5 2 4 6 8)

   (fapply (arr dec inc #(* 2 %)) [1 2 3 4])
   => (arr 0 1 2 3 2 3 4 5 2 4 6 8)

   (fapply {nil inc}
           {:a 1 :b 2 :c 3})
   => {:a 2 :b 3 :c 4}

   (fapply (obj :a inc) (obj :a 1 :b 1)) 
   => (obj :a 2 :b 1)

   (fapply (obj nil inc) (obj :a 1 :b 2 :c 3)) 
   => (obj :a 2 :b 3 :c 4)) 
