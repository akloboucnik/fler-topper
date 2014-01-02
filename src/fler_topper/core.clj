(ns fler-topper.core
  (:gen-class)
  (:require [clj-http.client :as client]
            [net.cgrand.enlive-html :as h]))

; TODO pull-up issuing of requests
(def ^:dynamic *cookie-store* (clj-http.cookies/cookie-store))

(defn login
  "Logs in to Fler"
  [username pwd]
  (client/post
    "http://www.fler.cz/uzivatel/prihlaseni"
    {:form-params {:username username
                   :pwd pwd
                   :_redir_after_login "http://www.fler.cz"}
     :cookie-store *cookie-store*}))

; TODO refactor regexps
(defn parse-funny-vars
  [body]
  "Parses properties from response body like `_sid`, `_uid` and `_checksum`"
  (let [sid (last (re-find #"_sid\W*=\W*['\"](.*)['\"]" body))
        uid (last (re-find #"_uid\W*=\W*['\"](.*)['\"]" body))
        checksum (last (re-find #"_checksum\W*=\W*['\"](.*)['\"]" body))]
    {:_sid sid :_uid uid :_checksum checksum :_dummy (quot (System/currentTimeMillis) 1000)}))


(defn get-product-page-params
  "Returns hash of params for issuing request to one product page"
  [page]
  (let [first-page-body (:body (client/get "http://www.fler.cz/moje-zbozi" {:cookie-store *cookie-store*}))]
    {:form-params (merge (parse-funny-vars first-page-body) {:page page :type "visible" :sort "add" :usercat "" :fulltext ""})
                                         :cookie-store *cookie-store*
                                         :query-params {:route "itemslist"}
                                        }))

(defn get-pages-count
  []
  "Returns integer - num of pages of products
   It has to do it funny-way - get /moje-zbozi, parse funny vars like _sid
   and POST /moje-zbozi to get actual paginator HTML to parse out number of pages"
  (let [response (client/post "http://www.fler.cz/moje-zbozi" (get-product-page-params 1))
        body (:body response)]
      (apply max (map #(Integer/parseInt (h/text %)) (h/select (h/html-snippet body) [:center :table :div :center])))
    ))

(defn get-products-from-page
  "retuns col of product ids on page"
  [page]
  (let [body (client/post "http://www.fler.cz/moje-zbozi" (get-product-page-params page))
        product-tags (h/select (h/html-snippet body) [[:div (h/attr? :data-id)]])]
    (map #(read-string (re-find #"\d+" (:data-id (:attrs %)))) product-tags)))


(defn get-all-products
  "return coll of all users product ids"
  []
  (mapcat get-products-from-page (range 1 (inc (get-pages-count)))))

(defn top
  "TOPs product"
  [product-id]
  (client/get "http://www.fler.cz/moje-zbozi" {:cookie-store *cookie-store* :query-params {:id product-id :a "top"}}))

(defn -main
  [& args]
  (login (first args) (second args))
  (let [product-id (rand-nth (get-all-products))]
    (top product-id)))

