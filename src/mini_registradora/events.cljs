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
         chave (get db :tipo-tela)]
     (-> db
         (assoc-in [:cadastro (keyword chave)] form-data)
         (dissoc :form)))))

(re-frame/reg-event-db
 ::consulta-form
  (fn [db]
    (let [form-data (:form db)
          chave (get db :tela-consulta)]
      (-> db
          (assoc-in [:consulta (keyword chave)] form-data)
          (dissoc :form)))))

(re-frame/reg-event-db
 ::update-consulta
 (fn [db [_ id val]]
   (assoc (dissoc db :form)  id val)))
