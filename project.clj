(defproject im.chit/purnam.category "0.4.0"
  :description "Category theory for Clojurescript"
  :url "http://www.github.com/purnam/purnam.category"
  :license {:name "The MIT License"
            :url "http://opensource.org/licencses/MIT"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [im.chit/purnam.native "0.4.0"]]
  :profiles {:dev {:dependencies [[org.clojure/clojurescript "0.0-2138"]
                                  [midje "1.6.0"]]
                   :plugins [[lein-cljsbuild "1.0.0"]]}}

  :cljsbuild {:builds [{:source-paths ["src", "test"]
                       :compiler {:output-to "target/purnam.category.js"
                                  :optimizations :whitespace
                                  :pretty-print true}}]})