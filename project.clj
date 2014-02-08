(defproject manager "0.1.0-SNAPSHOT"
  :description "utility for running services"
  :url "http://github.com/rasmuserik/manager"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/data.json "0.2.4"]
                 [clj-yaml "0.4.0"]
                 [hiccup "1.0.5"]
                 ]
  :main ^:skip-aot manager.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
