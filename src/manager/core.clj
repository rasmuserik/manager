(ns manager.core
  "manages running processes and web server configuration"
  (:gen-class)
  (:require
     [clj-yaml.core :as yaml]
     [manager.nginx :as nginx]
     [clojure.pprint :as pprint]
     [manager.logger :as logger]))

(defn createNginxConf []
  (spit "/home/server/nginx.conf" (nginx/generate)))
(createNginxConf)

(logger/log "hello" :world {:foo :bar :baz :quux})

(defn slurpyaml [name] (yaml/parse-string (slurp name)))
(defn flattenContent [data] (apply concat (apply concat (for [[section val] data]
  (for [[subsection val] val]
    (for [[id obj] val]
    (conj obj [:section section] [:subsection subsection] [:id id])))))))
(defn sortChrono [data] (sort-by #(:date %) data))

(comment
(def data (yaml/parse-string (slurp "content.yml")))
(def data (apply concat (apply concat (for [[section val] data]
  (for [[subsection val] val]
    (for [[id obj] val]
    (conj obj [:section section] [:subsection subsection] [:id id])))))))
(def data (sort-by #(:date %) data))
data

(pprint/pprint data)
  )

(defn -main
  [& args]
  (createNginxConf)
  (pprint/pprint (sortChrono (flattenContent (slurpyaml "content.yml")))))
