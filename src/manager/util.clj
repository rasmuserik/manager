(ns manager.util)

(defn exec [cwd & args]
  (-> (ProcessBuilder. args)
      (.directory (java.io.File. cwd))
      (.start)
      (.waitFor)))

