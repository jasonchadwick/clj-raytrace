(ns clj-raytrace.write-img
  (:require [clojure.string]))

(defn parse-triple [coll]
  (str (first coll) " " (second coll) " " (nth coll 2)))

; writes an image to a file, given a list of rows 
; (each row is a list of triples of ints)
; TODO: rewrite to use clojure.java.io.writer
(defn write-img [row-seq fname]
  (spit (str "output/" fname ".ppm")
        (str "P3 " (count row-seq) " " (count (first row-seq)) " 255 "
             (loop [rs row-seq acc-r ""]
               (if (empty? rs) acc-r
                   (let [col (loop [cs (first rs) acc-c ""]
                               (if (empty? cs) acc-c
                                   (recur (rest cs) (str acc-c (parse-triple (first cs)) " "))))]
                     (recur (rest rs) (str acc-r col))))))))

;test with linearly increasing 
(defn test-colors [w h]
  (write-img (loop [r w accr nil]
               (let [col (loop [c h accc nil]
                           (if (< c 0) accc
                               (recur (dec c) (cons `(~r ~c 0) accc))))]
                 (if (< r 0) accr
                     (recur (dec r) (cons col accr)))))
             "test-RG"))