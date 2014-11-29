(ns gol.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup []
  "Scene setup - is called only ones at start"
  (q/frame-rate 10)
  (q/color-mode :hsb)

  ; initial state
  {:life #{; Centinal 260 85 100
           ;[0 0] [1 0] [50 0] [51 0] [1 1] [50 1] [1 2] [3 2] [25 2] [26 2] [48 2] [50 2] [2 3] [3 3] [12 3] [25 3] [26 3] [39 3] [40 3] [48 3] [49 3] [11 4] [12 4] [39 4] [41 4] [10 5] [11 5] [41 5] [11 6] [12 6] [15 6] [16 6] [39 6] [40 6] [41 6] [11 10] [12 10] [15 10] [16 10] [39 10] [40 10] [41 10] [10 11] [11 11] [41 11] [11 12] [12 12] [39 12] [41 12] [2 13] [3 13] [12 13] [25 13] [26 13] [39 13] [40 13] [48 13] [49 13] [1 14] [3 14] [25 14] [26 14] [48 14] [50 14] [1 15] [50 15] [0 16] [1 16] [50 16] [51 16]
           ; 104P177 310 310 117
           [24 8] [37 8] [17 9] [18 9] [43 9] [44 9] [16 10] [17 10] [18 10] [22 10] [23 10] [38 10] [39 10] [43 10] [44 10] [45 10] [22 11] [23 11] [25 11] [26 11] [35 11] [36 11] [38 11] [39 11] [24 12] [37 12] [10 16] [51 16] [9 17] [10 17] [51 17] [52 17] [9 18] [10 18] [51 18] [52 18] [10 22] [11 22] [50 22] [51 22] [10 23] [11 23] [50 23] [51 23] [8 24] [12 24] [49 24] [53 24] [11 25] [50 25] [11 26] [50 26] [11 35] [50 35] [11 36] [50 36] [8 37] [12 37] [49 37] [53 37] [10 38] [11 38] [50 38] [51 38] [10 39] [11 39] [50 39] [51 39] [9 43] [10 43] [51 43] [52 43] [9 44] [10 44] [51 44] [52 44] [10 45] [51 45] [24 49] [37 49] [22 50] [23 50] [25 50] [26 50] [35 50] [36 50] [38 50] [39 50] [16 51] [17 51] [18 51] [22 51] [23 51] [38 51] [39 51] [43 51] [44 51] [45 51] [17 52] [18 52] [43 52] [44 52] [24 53] [37 53]
           ; 124P37 186 186 37
           ;[11 0] [12 0] [24 0] [25 0] [11 1] [12 1] [24 1] [25 1] [6 4] [30 4] [5 5] [7 5] [13 5] [23 5] [29 5] [31 5] [4 6] [7 6] [13 6] [15 6] [16 6] [20 6] [21 6] [23 6] [29 6] [32 6] [5 7] [6 7] [17 7] [19 7] [30 7] [31 7] [15 8] [17 8] [19 8] [21 8] [16 9] [20 9] [0 11] [1 11] [35 11] [36 11] [0 12] [1 12] [35 12] [36 12] [5 13] [6 13] [30 13] [31 13] [6 15] [8 15] [28 15] [30 15] [6 16] [9 16] [27 16] [30 16] [7 17] [8 17] [28 17] [29 17] [7 19] [8 19] [28 19] [29 19] [6 20] [9 20] [27 20] [30 20] [6 21] [8 21] [28 21] [30 21] [5 23] [6 23] [30 23] [31 23] [0 24] [1 24] [35 24] [36 24] [0 25] [1 25] [35 25] [36 25] [16 27] [20 27] [15 28] [17 28] [19 28] [21 28] [5 29] [6 29] [17 29] [19 29] [30 29] [31 29] [4 30] [7 30] [13 30] [15 30] [16 30] [20 30] [21 30] [23 30] [29 30] [32 30] [5 31] [7 31] [13 31] [23 31] [29 31] [31 31] [6 32] [30 32] [11 35] [12 35] [24 35] [25 35] [11 36] [12 36] [24 36] [25 36]
           ; 112P51 205 205 51
           ;[18 3] [19 3] [23 3] [24 3] [9 6] [10 6] [32 6] [33 6] [9 7] [10 7] [32 7] [33 7] [6 9] [7 9] [35 9] [36 9] [6 10] [7 10] [17 10] [18 10] [24 10] [25 10] [35 10] [36 10] [12 11] [13 11] [14 11] [16 11] [17 11] [25 11] [26 11] [28 11] [29 11] [30 11] [11 12] [13 12] [29 12] [31 12] [11 13] [12 13] [30 13] [31 13] [11 14] [31 14] [11 16] [31 16] [10 17] [11 17] [31 17] [32 17] [3 18] [10 18] [32 18] [39 18] [3 19] [39 19] [3 23] [39 23] [3 24] [10 24] [32 24] [39 24] [10 25] [11 25] [31 25] [32 25] [11 26] [31 26] [11 28] [31 28] [11 29] [12 29] [30 29] [31 29] [11 30] [13 30] [29 30] [31 30] [12 31] [13 31] [14 31] [16 31] [17 31] [25 31] [26 31] [28 31] [29 31] [30 31] [6 32] [7 32] [17 32] [18 32] [24 32] [25 32] [35 32] [36 32] [6 33] [7 33] [35 33] [36 33] [9 35] [10 35] [32 35] [33 35] [9 36] [10 36] [32 36] [33 36] [18 39] [19 39] [23 39] [24 39]
           }
   :cell-size 5 ; size of cell in pixels
   :frame 1 ; frame number
   })

(defn neighbors [[x y]]
  "Get 8 neighbors of point [x y]"
  (for [dx [-1 0 1]
        dy [-1 0 1]
        :when (not= 0 dx dy)]
    [(+ x dx) (+ y dy)]))

(defn square [[x y]]
  "Gets a 3x3 square of points with center at point [x y]"
  (for [dx [-1 0 1]
        dy [-1 0 1]]
    [(+ x dx) (+ y dy)]))

(defn alive-neighbors [life point]
  "Counts alive neighbors for point [x y]"
  (->> (neighbors point)
       (filter #(contains? life %)) ; alive neighbors
       count))

(defn will-live? [life point]
  "Checks whether point will live for current life state"
  (let [n (alive-neighbors life point)]
    (or (= n 3) (and (= n 2) (contains? life point)))))

(defn update [state]
  "Calculates next state"
  (let [life (:life state)
        candidates (set (mapcat square life)) ; all alive points with their neighbors
        next-life (set (filter #(will-live? life %) candidates))] ; checks every candadate whether it will live
    (assoc state :life next-life :frame (inc (:frame state)))) ; update state with next-life and increment frame
  )

(defn draw [state]
  "Draws current state"

  (q/background 240) ; background color
  (q/stroke 220) ; cell border color
  (q/fill 180) ; cell body color

  (doseq [[x y] (:life state)
          :let [w (:cell-size state)]]
    (q/rect (* w x) (* w y) w w 2))

  ;(if (<= (:frame state) 117) (q/save-frame "frame-####.png"))
  )

(defn mouse-wheel [state event]
  "Zooms scene by changing cell size and shift scene to keep the same point under the cursor"
  (let [w (:cell-size state)
        nw (max (+ (- event) w) 1)
        mx (q/mouse-x)
        my (q/mouse-y)
        dx (- (quot mx w) (quot mx nw))
        dy (- (quot my w) (quot my nw))
        next-life (set (map (fn [[x y]] [(- x dx) (- y dy)]) (:life state)))]
    (assoc state :cell-size nw :life next-life)))

(defn mouse-dragged [state event]
  "Mouse handler to add new lives into the scene"
  (let [w (:cell-size state)
        x (quot (:x event) w)
        y (quot (:y event) w)]
    (assoc state :life (conj (:life state) [x y]))))

(q/defsketch gol
  :title "Game of life"
  :size [310 310]
  :features [:resizable]
  :setup setup
  :update update
  :draw draw
  :mouse-wheel mouse-wheel
  :mouse-dragged mouse-dragged
  :middleware [m/fun-mode])
