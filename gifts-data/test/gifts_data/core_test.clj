(ns gifts-data.core-test
  (:require [clojure.test :refer :all]
            [gifts-data.core :refer :all]))

(deftest country-applicants-test
  (testing "takes country vector and returns applicants"
    (is (= [["US" true] ["US" true] ["US" false]]
           (country-applicants ["US" 2 1])))))

(deftest prepend-ids-test
  (testing "returns vectors with ids in the first position"
    (is (= [[1 "US" true] [2 "US" true]]
           (prepend-ids [["US" true] ["US" true]])))))
