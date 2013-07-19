(ns gifts.core
  (require [clojure.data.csv :as csv]
           [clojure.java.io :as io]
           [clojure.set :as s]))

(defn parse-applicant
  [[id country flag]]
  [(Integer. id) country (if (#{"true"} flag) true false)])

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

(defn split-applicants
  [seqs]
  (let [c (apply + (rest (reverse (sort (map count seqs)))))]
    [(map #(take c %) seqs)
     (map #(drop c %) seqs)]))

(defn matcher [count seqs]
  (let [m (fn [acc c ss]
            (if (or (zero? c) (empty? ss))
              [acc ss]
              (recur (conj acc (ffirst ss))
                     (dec c)
                     (filter (complement empty?) (conj (vec (rest ss)) (rest (first ss)))))))]
    (m [] count seqs)))

(defn country-match [group seqs]
  (let [k (second (first group))
        gss (group-by second (apply concat seqs))
        [r ss] (matcher (count group) (vals (dissoc gss k)))]
    [(zipmap group r) (conj ss (gss k))]))

(defn international-match [seqs]
  (let [rev-sort #(reverse (sort-by count %))
        m (fn [acc countries ss]
            (if (empty? countries)
              acc
              (let [[r s] (country-match (first countries) ss)]
                (recur (conj acc r) (rest countries) (rev-sort s)))))]
    (m {} (rev-sort seqs) seqs)))

(defn match [applicant-data]
  (let [a (vals (group-by second applicant-data))
        [b c] (split-applicants (map #(filter last %) a))]
    (conj {}
          (international-match b)
          (mapcat #(rotate-zipmap % 1) (map #(filter (complement last) %) a))
          (mapcat #(rotate-zipmap % 1) c))))

(defn match-analysis [coll]
  (println "Unique match keys:   " ((comp count set keys) coll))
  (println "Unique match targets:" ((comp count set vals) coll))
  (println "Willing to willing (international): "
           (count (filter #(and (last (first %))
                                (last (last %))
                                ((complement =) (second (first %)) (second (last %)))) coll)))
  (println "Willing to willing (national):      "
           (count (filter #(and (last (first %))
                                (last (last %))
                                (= (second (first %)) (second (last %)))) coll)))
  (println "Non-willing to non-willing (international): "
           (count (filter #(and ((complement last) (first %))
                                ((complement last) (last %))
                                ((complement =) (second (first %)) (second (last %)))) coll)))
  (println "Non-willing to non-willing (national):      "
           (count (filter #(and ((complement last) (first %))
                                ((complement last) (last %))
                                (= (second (first %)) (second (last %)))) coll))))

(defn -main [& args]
  (let [applicants (with-open [in-file (io/reader (first args))]
                     (map parse-applicant (doall (csv/read-csv in-file))))
        matches (match applicants)]
    (match-analysis matches)
    (with-open [out-file (io/writer (last args))]
      (csv/write-csv out-file (map #(apply concat %) matches)))))
