(ns manager.core
  "manages running processes and web server configuration"
  (:gen-class)
  (:require
     [clj-yaml.core :as yaml]
     [manager.nginx :as nginx]
     [manager.logger :as logger]))

(defn createNginxConf []
  (spit "/home/server/nginx.conf" (nginx/generate)))
(createNginxConf)

(logger/log "hello" :world {:foo :bar :baz :quux})

(defn -main
  [& args]
  (createNginxConf)
  (println (yaml/parse-string (slurp "content.yml"))))
