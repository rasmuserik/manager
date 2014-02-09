(ns manager.core
  "manages running processes and web server configuration"
  (:gen-class)
  (:require
   [clj-yaml.core :as yaml]
   [manager.nginx :as nginx]
   [manager.gh :as gh]
   [hiccup.core :as hiccup]
   [clojure.string]
   [clojure.pprint :as pprint]
   [manager.logger :as logger]))

(defn createNginxConf []
  (spit "/home/server/nginx.conf" (nginx/generate)))
(createNginxConf)

(logger/log "hello" :world {:foo :bar :baz :quux})

(defn slurpyaml [name] (yaml/parse-string (slurp name)))
(defn flattenContent [data]
  (apply
   concat
   (apply
    concat
    (for [[section val] data]
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


;(hiccup/html [:span "hello"])
(defn -main
  [& args]
  (createNginxConf)
  (let [data (sortChrono (flattenContent (slurpyaml "content.yml")))
        duplicates (duplicateIds data)
        repos (seq (gh/repos data))]
    (prn (pmap gh/fetch repos))
    (prn (reduce into #{} (map keys data)))
    (if duplicates (prn "duplicates:" duplicates))))
