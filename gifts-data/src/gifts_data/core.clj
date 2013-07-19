(ns gifts-data.core
  (require [clojure.java.io :as io]
           [clojure.data.csv :as csv]))

(defn country-applicants
  [coll]
  (let [c (first coll)]
    (concat (repeat (second coll) [c true])
            (repeat (last coll) [c false]))))

(defn prepend-ids
  [coll]
  (map #(concat [%] %2) (range 1 (inc (count coll))) coll))

(defn parse-row
  [row]
  [(first row) (Integer. (second row)) (Integer. (last row))])

(defn generate
  [rows]
  ((comp prepend-ids
         (partial apply concat)
         (partial map country-applicants)
         (partial map parse-row))
   rows))

(defn -main [& args]
  (with-open [in-file (io/reader (first args))]
    (with-open [out-file (io/writer (second args))]
      (csv/write-csv out-file (generate (rest (csv/read-csv in-file)))))))
