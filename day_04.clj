(ns day-04
  (:require [clojure.set :as set]
            [clojure.string :as str]))

(defn parse-range [input]
  (apply (fn [l u] (set (range (Integer/parseInt l) (+ (Integer/parseInt u) 1))))
         (str/split input #"-")))

(defn parse-assignments [input]
  (map (fn [pair] (map parse-range (str/split pair #",")))
       (str/split (str/trim input) #"\r?\n")))

(defn full-overlap? [assignments]
  (= (count (apply set/union assignments)) (apply max (map count assignments))))

(defn partial-overlap? [assignments]
  (> (count (apply set/intersection assignments)) 0))

(def assignments (parse-assignments (slurp "day_04.txt")))

; part 1
(count (filter full-overlap? assignments))

; part 2
(count (filter partial-overlap? assignments))
