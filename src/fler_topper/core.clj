(ns fler-topper.core
  (:gen-class)
  (:require [org.httpkit.client :as http]))


(defn -main [& args]
  (prn args)
  (let [req (http/get "http://www.fler.cz/")]
    (prn "Status: " (:set-cookie (:headers @req)))))

