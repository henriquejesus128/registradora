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
 (fn  [db [_ form-key]]
    (every? #(get-in db [:form %])  form-key)))


(re-frame/reg-sub
 ::db-tipo
 (fn [db]
   (:tipo db)))
