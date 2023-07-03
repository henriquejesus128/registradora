(ns mini-registradora.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 ::form
 (fn  [db [_ id]]
   (get-in db [:form id] "")))

(re-frame/reg-sub
 ::form-is-valid?
 (fn  [db [_ form-ids]]
   (let [_ (prn "is valid subs" (get-in db [:form :tipo]))] (every? #(get-in db [:form %])  form-ids))))
