(ns cadence.config
  (:require [clojure.string :as string]
            [clojure.java.io :as io])
  (:import [java.net URL]))

(def config-from-file
  (if-let [cres (io/resource "config.clj")]
    (-> cres (.getPath) (load-file)) {}))

(defn read-config [config-var]
  (get config-from-file
       (keyword (string/replace
                  (string/lower-case config-var)
                  "_" "-")) (System/getenv config-var)))

(def storage {:uri (read-config "MONGOHQ_URL")
              :db-name (read-config "MONGO_DB_NAME")
              :username (read-config "MONGO_USERNAME")
              :password (read-config "MONGO_PASSWORD")})

(def tokens
  {:twitter
   {:token (read-config "TWITTER_OAUTH_TOKEN")
    :secret-token (read-config "TWITTER_OAUTH_SECRET_TOKEN")}})
