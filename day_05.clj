(ns day-05
  (:require [clojure.string :as str]))

(defn parse-stack-labels [input]
  (map second (re-seq #" (\d+) " input)))

(defn parse-stack-row [input]
  (map second (re-seq #"(?:\[(\w)\])|(?:   ) ?" input)))

(defn parse-drawing [input]
  (let [lines (str/split input #"\r?\n")
        contents (drop-last lines)
        labels (last lines)]
    (zipmap (parse-stack-labels labels)
            (apply map #(remove nil? %&) (map parse-stack-row contents)))))

(defn parse-moves [input]
  (map (partial apply (fn [c s d] {:count (Integer/parseInt c), :from s, :to d}))
       (map rest (re-seq #"move (\d+) from (\d+) to (\d+)" input))))

(defn cratemover-9000 [state move]
  (loop [state state, iterations (:count move)]
    (let [from (:from move), to (:to move), crate (first (get state from))]
      (if (<= iterations 0) state
          (recur (assoc state
                        from (rest (get state from))
                        to (cons crate (get state to)))
                 (- iterations 1))))))

(defn cratemover-9001 [state move]
  (let [count (:count move), from (:from move), to (:to move)]
    (assoc state
           from (drop count (get state from))
           to (concat (take count (get state from)) (get state to)))))

(def input-sections (str/split (slurp "day_05.txt") #"\r?\n\r?\n"))
(def initial-state (parse-drawing (first input-sections)))
(def moves (parse-moves (second input-sections)))

; part 1
(str/join (map #(first (second %)) (sort-by key (reduce cratemover-9000 initial-state moves))))

; part 2
(str/join (map #(first (second %)) (sort-by key (reduce cratemover-9001 initial-state moves))))
