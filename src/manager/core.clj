(ns manager.core (:gen-class))
(require '[clj-yaml.core :as yaml])


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (yaml/parse-string (slurp "index.yaml"))))
