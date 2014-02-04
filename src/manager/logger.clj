(ns manager.logger
  "Utility for logging"
  (:require [clojure.data.json :as json]))

(defn log [& args]
  (.println System/out (json/write-str args))
  prn args)
