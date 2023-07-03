(ns mini-registradora.events
  (:require
   [re-frame.core :as re-frame]
   [mini-registradora.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced]]
   ))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced []
   db/default-db))

(re-frame/reg-event-db
 ::update-form
 (fn [db [_ id val]]
   (assoc-in db [:form id] val)))

(re-frame/reg-event-db
 ::update-db
 (fn [db [_ id val]]
   (assoc db  id val)))

(re-frame/reg-event-db
 ::save-form
 (fn [db]
   (let [form-data (:form db)
         atributos (get db :atributos [] )
         update-atributos (conj atributos form-data)
         _ (prn "form-data?" form-data)]
 (-> db
     (assoc :atributos update-atributos)
     (dissoc :form)))))
