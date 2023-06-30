(ns mini-registradora.views
  (:require
   [re-frame.core :as re-frame]
   [mini-registradora.subs :as subs]
   ))


(defn text-input []
  [:form
   [:div
    [:h1 {:class "teste"} "Registro de CDB"]
    [:label "ID participante"]
    [:input {:type "text"}]
    [:label "CNPJ/CFP"]
    [:input {:type "text"}]
    [:label "Tipo de Regime"]
    [:input {:type "text"}]
    [:label "Data de Emissão"]
    [:input {:type "text"}]
    [:label "Data de Vencimento"]
    [:input {:type "text"}]
    [:label "Quantidade"]
    [:input {:type "text"}]
    [:label "Valor unitario de Emissão"]
    [:input {:type "text"}]
    [:label "Codigo ISIN"]
    [:input {:type "text"}]
    [:button "save"]]])


(defn main-panel []
    [:div
     [text-input]])
