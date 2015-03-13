(defproject im.chit/brahmin "0.3.3-SNAPSHOT"
  :description "Category theory morphisms for clojurescript"
  :url "http://www.github.com/purnam/brahmin"
  :license {:name "The MIT License"
            :url "http://opensource.org/licencses/MIT"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [im.chit/purnam.core "0.5.2"]
                 [im.chit/purnam.native "0.5.2"]]
  :profiles {:dev {:dependencies [[org.clojure/clojurescript "0.0-3058"]
                                  [im.chit/purnam.test "0.5.2"]
                                  [midje "1.6.3"]]
                   :plugins [[lein-ancient "0.5.5"]
                             [lein-midje "3.1.3"]
                             [lein-cljsbuild "1.0.3"]
                             [lein-midje-doc "0.0.24"]]}}
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