(ns manager.core
  "manages running processes and web server configuration"
  (:gen-class)
  (:require
   [clj-yaml.core :as yaml]
   [manager.nginx :as nginx]
   [hiccup.core :as hiccup]
   [clojure.string]
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

(defn duplicateIds [data]
  (loop [ids #{}
         dups '()
         [elem & elems] data]
    (if elem
      (let [id (elem :id)]
        (if (and id (ids id))
          (recur ids (conj dups id) elems)
          (recur (conj ids id) dups elems)))
      dups)))

(def data (reverse (sortChrono (flattenContent (slurpyaml "content.yml")))))

(let [duplicates (duplicateIds data)]
  (if duplicates
    (prn "duplicates:" duplicates)))

(count data)
;(def data (filter #(% :date) (reverse data)))
(count data)
(map #(% :id) data)

(keys (reduce into data))

(def
  gh-repos
  ( into
    #{}
    ( ->> data
      (map #(% :github))
      (filter identity)
      (map #(if (= -1 (.indexOf % "/")) (str "rasmuserik/" %) %)))))

(defn gh-fetch [repos]
  "download/update repository from github"
  (let [base "/home/server/repos"
        name ((clojure.string/split repos #"/") 1)
        reposDir (str base "/" name)
        url (str "https://github.com/" repos ".git")]
    (prn (str "start " name))
    (-> (ProcessBuilder. ["git" "clone" url])
        (.directory (java.io.File. base))
        (.start)
        (.waitFor))
    (-> (ProcessBuilder. ["git" "pull"])
        (.directory (java.io.File. reposDir))
        (.start)
        (.waitFor))
    ;(.waitFor (-> pb (.directory (java.io.File. "/home/server/repos")) (.start)))
    (prn (str "done " url))))
(clojure.string/split "foo/bar" #"/")
(gh-fetch "rasmuserik/manager")

(pmap gh-fetch gh-repos)

(hiccup/html [:span "hello"])

(defn -main
  [& args]
  (createNginxConf)
  (let [data (sortChrono (flattenContent (slurpyaml "content.yml")))
        duplicates (duplicateIds data)]
    (pprint/pprint data)
    (if duplicates (prn "duplicates:" duplicates))))
