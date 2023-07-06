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
   (assoc (dissoc db :form)  id val)))

(re-frame/reg-event-db
 ::save-form
 (fn [db]
   (let [form-data (:form db)
         atributos (get db :api {})
         update-atributos (conj atributos form-data)]
 (-> db
     (assoc :api update-atributos)
     (dissoc :form)))))

(re-frame/reg-event-db
 ::consulta-form
 (fn [db [data-json]]
   (let [form-data (:form db)
         update-atributos (conj data-json form-data)]
     (-> db
         (assoc :form update-atributos)))))
