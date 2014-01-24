(defproject fler-topper "0.1.0-SNAPSHOT"
  :description "Top Fler items randomly"
  :url "http://kloboucnik.me"
  :min-lein-version "2.0.0"
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :main fler-topper.core
  :uberjar-name "fler-topper-standalone.jar"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [clj-http "0.7.8"]
                 [enlive "1.1.5"]])
