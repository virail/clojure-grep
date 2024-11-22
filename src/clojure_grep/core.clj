(ns clojure-grep.core
  (:gen-class))

(defn read-file
  [path]
  (set (clojure.string/split-lines (slurp path))))

;; (defn found-in-file?
;;   [set pattern]
;;   (some #(re-find pattern %) set))

(defn found-in-line?
  [line pattern]
  (re-find pattern line))

(defn found-in-file?
  [set pattern]
  (if (empty? set)
    nil 
    (let [curr (first set) 
          remaining (rest set)] 
      (if (found-in-line? curr pattern) 
        (first set) 
        (recur remaining pattern)))))

(defn -main 
  [& args]
  (let [line (found-in-file? (read-file (first args)) (re-pattern (second args)))]
    (if line
      (println line)
      (println "Pattern not found"))))
