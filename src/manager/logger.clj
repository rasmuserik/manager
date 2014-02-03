(ns manager.logger)
(require '[clojure.data.json :as json])

(defn log [& args]
  (.println System/out (json/write-str args))
  prn args)
