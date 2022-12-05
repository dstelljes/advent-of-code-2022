(ns day-02)

(def ^:const rock 0)
(def ^:const paper 1)
(def ^:const scissors 2)

(def ^:const loss 0)
(def ^:const draw 3)
(def ^:const win 6)

(def opposing-choices {"A" rock, "B" paper, "C" scissors})
(def my-choices {"X" rock, "Y" paper, "Z" scissors})
(def desired-outcomes {"X" loss, "Y" draw, "Z" win})

(defn parse-guide [first-col second-col input]
  (map (fn [match] [(first-col (nth match 1)) (second-col (nth match 2))])
       (re-seq #"([ABC]) ([XYZ])" input)))

(defn calculate-outcome [opposing-choice my-choice]
  (condp = opposing-choice
    (mod (+ my-choice 1) 3) loss
    my-choice draw
    win))

(defn calculate-choice [opposing-choice desired-outcome]
  (condp = desired-outcome
    loss (mod (+ opposing-choice 2) 3)
    draw opposing-choice
    win (mod (+ opposing-choice 1) 3)))

(defn score [outcome my-choice]
  (+ outcome my-choice 1))

(def input (slurp "day_02.txt"))

; part 1
(reduce + (map (partial apply (fn [o m] (score (calculate-outcome o m) m)))
               (parse-guide opposing-choices my-choices input)))

; part 2
(reduce + (map (partial apply (fn [o d] (let [m (calculate-choice o d)] (score d m))))
               (parse-guide opposing-choices desired-outcomes input)))
