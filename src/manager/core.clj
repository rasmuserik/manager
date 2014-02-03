(ns manager.core (:gen-class))
(require '[clj-yaml.core :as yaml]
         '[manager.nginx :as nginx]
         '[manager.logger :as logger])
(defn createNginxConf [] spit "/home/server/nginx.conf" nginx/generate)
(logger/log "hello" :world {:foo :bar :baz :quux})


(defn -main
  [& args]
  (createNginxConf)
  (println (yaml/parse-string (slurp "content.yml"))))
