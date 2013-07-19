(ns gifts.core-test
  (:require [clojure.test :refer :all]
            [gifts.core :refer :all]))

(deftest rotate-zipmap-test
  (testing "rotates and zipmaps the collection"
    (is (= {1 2, 2 1} (rotate-zipmap [1 2] 1)))))

(deftest international-match-test
  (testing "matches international applicants"
    (is (=
         {"1a" "1b" "2a" "1c" "3a" "2b" "1b" "1a" "2b" "2a" "1c" "3a"}
         (international-match [["1a" "2a" "3a"] ["1b" "2b"] ["1c"]])))))

(deftest split-applicants-test
  (testing "caps the amount of applicants from a country for all countries"
    (is (=
         [
          [[1 1 1] [2 2] [3]]
          [[] [] []]
         ]
         (split-applicants [[1 1 1] [2 2] [3]]))))
    (is (=
         [
          [[1 1 1] [2 2] [3]]
          [[1] [] []]
         ]
         (split-applicants [[1 1 1 1] [2 2] [3]]))))

(deftest matcher-test
  (testing "'interleaves' seqs and returns result alongside remaining elements"
    (is (=
         [[1 2 3 1], [[2] [3] [1 1]]]
         (matcher 4 [[1 1 1 1] [2 2] [3 3]])))))

