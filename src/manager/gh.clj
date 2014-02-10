(ns manager.gh
  "Working with github repositories"
  (:require [clojure.string]))

(defn repos [data]
  "Extract names of github repositories in a set of data"
  (into #{}
        (->> (map #(% :github) data)
             (filter identity)
             (map #(if (= -1 (.indexOf % "/")) (str "rasmuserik/" %) %)))))

(defn fetch [repos]
  "Download/update repository from github"
  (let [base "/home/server/repos"
        name ((clojure.string/split repos #"/") 1)
        reposDir (str base "/" name)
        url (str "https://github.com/" repos ".git")]
    (-> (ProcessBuilder. ["git" "clone" url])
        (.directory (java.io.File. base))
        (.start)
        (.waitFor))
    (-> (ProcessBuilder. ["git" "pull"])
        (.directory (java.io.File. reposDir))
        (.start)
        (.waitFor))
    repos
    ))
