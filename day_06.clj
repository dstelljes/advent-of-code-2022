(ns day-06
  (:require [clojure.string :as str]))

(def ^:const sop-length 4)
(def ^:const som-length 14)

(defn index-of-distincts [length input]
  (first (filter (fn [i] (= (count (set (take length (drop i input)))) length))
                 (range (count input)))))

(def datastream (str/trim (slurp "day_06.txt")))

; part 1
(+ (index-of-distincts sop-length datastream) sop-length)

; part 2
(+ (index-of-distincts som-length datastream) som-length)
