(ns day-01
  (:require [clojure.string :as str]))

(defn parse-lists [input]
  (map (fn [list] (map #(Integer/parseInt %) (str/split list #"\r?\n")))
       (str/split (str/trim input) #"\r?\n\r?\n")))

(defn total-calories [lists]
  (sort > (map (partial reduce +) lists)))

(def totals (total-calories (parse-lists (slurp "day_01.txt"))))

; part 1
(first totals)

; part 2
(reduce + (take 3 totals))
