(ns gifts.core
  (use [clojure.tools.cli :only [cli]])
  (require [clojure.data.csv :as csv]
           [clojure.java.io :as io]
           [clojure.set :as s]
           [gifts.data-gen :as d]))

(def raw-applicants
  (with-open [in-file (io/reader "applicants.csv")]
    (doall
     (csv/read-csv in-file))))

(defn read-applicant
  [[id country flag]]
  [(Integer. id) country (if (#{"true"} flag) true false)])

(def applicants
  (map read-applicant raw-applicants))

(defn group-by-willing
  [coll flag]
  (group-by second
            (filter #(= flag (last %)) applicants)))

(def willing
  (group-by-willing applicants true))

(def not-willing
  (group-by-willing applicants false))

(defn rotate 
  "Take a sequence and left rotates it n steps. If n is negative, the collection is rotated right."
  [n coll] 
  (let [c (count coll)]
    (cond
     (zero? c) coll
     :else
     (take c (drop (mod n c) (cycle coll))))))

(defn rotate-zipmap
  [coll i]
  (zipmap coll (rotate i coll)))

(defn -main [& args]
  (cli args)
  (comment
    (with-open [out-file (io/writer "out-file.csv")]
      (csv/write-csv out-file ((comp (partial map flatten) first) (international-match (vals willing)))))))

(defn interleave-all
  "Returns a lazy seq of the first item in each coll, then the second etc."
  {:added "1.0"
   :static true}
  ([c] c)
  ([c1 c2]
     (lazy-seq
      (let [s1 (seq c1) s2 (seq c2)]
        (cond (and s1 s2)
          (cons (first s1) (cons (first s2) 
                                 (interleave-all (rest s1) (rest s2))))
          :else (concat s1 s2)))))
  ([c1 c2 & colls] 
     (lazy-seq 
      (let [ss (filter identity (map seq (conj colls c2 c1)))]
        (concat (map first ss) (apply interleave-all (map rest ss)))))))

(defn international-match
  [coll]
  (let [sort-count (fn [x] (reverse (sort-by count x)))
        imatch (fn [prev acc colls]
                 (if (empty? colls)
                   acc
                   (let [c (first colls)
                         r (apply interleave-all (concat prev (rest colls)))
                         v (take (count c) r)
                         z ((comp sort-count
                                  vals
                                  #(group-by second %)
                                  #(s/difference (set %) (set v)))
                            (apply concat (rest colls)))]
                     (recur (conj prev c)
                            (conj acc (zipmap c v))
                            z))))]
    (imatch [] {} (sort-count coll))))
