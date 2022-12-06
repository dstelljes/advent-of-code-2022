(ns day-03
  (:require [clojure.set :as set]
            [clojure.string :as str]))

(defn parse-contents [input]
  (map (fn [rucksack] (partition (/ (count rucksack) 2) (map int rucksack)))
       (str/split (str/trim input) #"\r?\n")))

(defn calculate-priority [item]
  (- item (condp <= item
            (int \a) 96
            (int \A) 38)))

(defn find-common-items [collections]
  (apply set/intersection (map set collections)))

(def contents (parse-contents (slurp "day_03.txt")))

; part 1
(reduce + (map calculate-priority (apply concat (map find-common-items contents))))

; part 2
(reduce + (map calculate-priority (apply concat (map find-common-items (map (partial map flatten) (partition 3 contents))))))
