(ns manager.core
  "manages running processes and web server configuration"
  (:gen-class)
  (:require
   [clj-yaml.core :as yaml]
   [hiccup.core :as hiccup]
   [clojure.string]
   [clojure.pprint :as pprint]
   [manager.nginx :as nginx]
   [manager.content :as content]
   [manager.gh :as gh]
   [manager.logger :as logger]))


;(hiccup/html [:span "hello"])
;(logger/log "hello" :world {:foo :bar :baz :quux})
(defn -main
  [& args]
  (let [data (content/load "content.yml")
        duplicates (content/duplicateIds data)
        repos (seq (gh/repos data))]
    ;(prn (pmap gh/fetch repos))
    (spit "/home/server/nginx.conf" (nginx/generate data))
    (prn (reduce into #{} (map keys data)))
    (if duplicates (prn "duplicates:" duplicates))))
