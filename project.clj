(defproject im.chit/brahmin "0.3.1"
  :description "Category theory morphisms for clojurescript"
  :url "http://www.github.com/purnam/brahmin"
  :license {:name "The MIT License"
            :url "http://opensource.org/licencses/MIT"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [im.chit/purnam.native "0.4.3"]]
  :profiles {:dev {:dependencies [[org.clojure/clojurescript "0.0-2342"]
                                  [im.chit/purnam.core "0.4.3"]
                                  [im.chit/purnam.test "0.4.3"]
                                  [midje "1.6.3"]]
                   :plugins [[lein-ancient "0.5.5"]
                             [lein-cljsbuild "1.0.3"]]}}
  :documentation {:files {"doc/index"
                          {:input "test/brahmin/api_guide.clj"
                           :title "brahmin"
                           :sub-title "Category theory morphisms for clojurescript"
                           :author "Chris Zheng"
                           :email "z@caudate.me"
                           :tracking "UA-31320512-2"}}}
  :cljsbuild {:builds [{:source-paths ["src", "test"]
                        :compiler {:output-to "target/brahmin.js"
                                   :optimizations :whitespace
                                   :pretty-print true}}]})