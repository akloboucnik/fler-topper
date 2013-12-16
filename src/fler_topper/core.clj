(ns fler-topper.core
  (:gen-class)
  (:require [clj-http.client :as client]
            [net.cgrand.enlive-html :as h]))

; TODO pull-up issuing of requests
; TODO fix global cookie-store
(def cookie-store (clj-http.cookies/cookie-store))

(defn login
  "Logs in to Fler"
  [username pwd]
  (client/post
    "http://www.fler.cz/uzivatel/prihlaseni"
    {:form-params {:username username
                   :pwd pwd
                   :_redir_after_login "http://www.fler.cz"}
     :cookie-store cookie-store}))

; TODO refactor regexps
(defn parse-funny-vars
  [body]
  "Parses properties from response body like `_sid`, `_uid` and `_checksum`"
  (let [sid (last (re-find #"_sid\W*=\W*['\"](.*)['\"]" body))
        uid (last (re-find #"_uid\W*=\W*['\"](.*)['\"]" body))
        checksum (last (re-find #"_checksum\W*=\W*['\"](.*)['\"]" body))]
    {:_sid sid :_uid uid :_checksum checksum :_dummy (quot (System/currentTimeMillis) 1000)}))

(defn get-pages-count
  []
  "Returns integer - num of pages of products
   It has to do it funny-way - get /moje-zbozi, parse funny vars like _sid
   and POST /moje-zbozi to get actual paginator HTML to parse out number of pages"
  (let [first-page-body (:body (client/get "http://www.fler.cz/moje-zbozi" {:cookie-store cookie-store}))
        req-params {:form-params (merge (parse-funny-vars first-page-body) {:page 1 :type "visible" :sort "add" :usercat "" :fulltext ""})
                     :cookie-store cookie-store
                     :query-params {:route "itemslist"}
                    }
        response (client/post "http://www.fler.cz/moje-zbozi" req-params)
        body (:body response)]
      (apply max (map #(Integer/parseInt (h/text %)) (h/select (h/html-snippet body) [:center :table :div :center])))
    ))

(defn -main
  [& args]
  (prn args))

