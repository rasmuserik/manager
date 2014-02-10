(ns manager.content
  (:require
   [manager.util :as util]
   [clj-yaml.core :as yaml]))

(defn slurpyaml [name]
  "read and parse a yaml-file"
  (yaml/parse-string (slurp name)))

(defn add-properties [data]
  "add :id, :section and :subsection based on yaml data structure"
  (for [[section val] data]
      (for [[subsection val] val]
        (for [[id obj] val]
          (conj obj [:section section] [:subsection subsection] [:id id])))))

(defn flatten-content [data]
  (apply concat (apply concat data)))

(defn sort-chrono [data] (reverse (sort-by #(:date %) data)))

(defn load-content [filename]
  (->> (slurpyaml filename)
       (add-properties)
       (flatten-content)
       (sort-chrono)))

(defn extract-prop [data prop]
  (into #{} (filter identity (map prop data))))

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

; test/experiments


(def basedir "/home/server/")
(def make-link [from to]
  (util/exec basedir "ln" "-sf"
             (str basedir "repos/" from)
             (str basedir "www/" to)))

(def data (load-content "content.yml"))
(extract-prop data :online)
