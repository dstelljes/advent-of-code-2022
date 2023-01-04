(ns day-07
  (:require [clojure.string :as str]))

(def ^:const small-threshold 100000)
(def ^:const total-space 70000000)
(def ^:const desired-space 30000000)

(defn change-directory [next segments]
  (condp = next
    "/" []
    ".." (pop segments)
    (conj segments next)))

(defn write [path name value tree]
  (assoc-in tree (conj path name) value))

(defn parse-tree [input]
  (loop [path [] tree {} lines (str/split input #"\r?\n")]
    (if (= (count lines) 0) tree
        (let [words (str/split (first lines) #" ") remaining (rest lines)]
          (condp = (nth words 0)
            "$" (condp = (nth words 1)
                  "cd" (recur (change-directory (nth words 2) path) tree remaining)
                  "ls" (recur path tree remaining))
            "dir" (recur path (write path (nth words 1) {} tree) remaining)
            (recur path (write path (nth words 1) (Integer/parseInt (nth words 0)) tree) remaining))))))

(defn compute-sizes [directory]
  (let [children (into {} (apply concat
                                 (for [[name value] directory :when (map? value)]
                                   (map #(update % 0 (partial cons name)) (compute-sizes value)))))]
  (assoc children '() (reduce + (concat (filter int? (vals directory))
                                        (for [[path value] children :when (= 1 (count path))] value))))))

(def tree (parse-tree (slurp "day_07.txt")))
(def sizes (compute-sizes tree))

; part 1
(reduce + (filter #(<= % small-threshold) (vals sizes)))

; part 2
(apply min (filter #(>= % (- desired-space (- total-space (get sizes '())))) (vals sizes)))
