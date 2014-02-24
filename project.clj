(defproject im.chit/brahmin "0.4.0"
  :description "Category theory morphisms for clojurescript"
  :url "http://www.github.com/purnam/brahmin"
  :license {:name "The MIT License"
            :url "http://opensource.org/licencses/MIT"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [im.chit/purnam.native "0.4.0"]]
  :profiles {:dev {:dependencies [[org.clojure/clojurescript "0.0-2138"]
                                  [im.chit/purnam.core "0.4.0"]
                                  [im.chit/purnam.test "0.4.0"]
                                  [midje "1.6.0"]]
                   :plugins [[lein-cljsbuild "1.0.0"]]}}
  :cljsbuild {:builds [{:source-paths ["src", "test"]
                       :compiler {:output-to "target/brahmin.js"
                                  :optimizations :whitespace
                                  :pretty-print true}}]})