(ns gifts.core-test
  (:require [clojure.test :refer :all]
            [gifts.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 1 1))))

(deftest rotate-zipmap-test
  (testing "rotates and zipmaps the collection"
    (is (= {1 2, 2 1} (rotate-zipmap [1 2] 1)))))

(deftest international-match-test
  (testing "matches international applicants"
    (is (=
         {"1a" "1b" "2a" "1c" "3a" "2b" "1b" "1a" "2b" "2a" "1c" "3a"}
         (international-match [["1a" "2a" "3a"] ["1b" "2b"] ["1c"]])))))
