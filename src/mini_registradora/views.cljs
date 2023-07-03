(ns mini-registradora.views
  (:require
   [re-frame.core :as re-frame]
   [mini-registradora.subs :as subs]
   [mini-registradora.events :as events]))

(defn text-input [id label]
  (let [value @(re-frame/subscribe [::subs/form id])]
     [:div.div
      [:label.texto label]
      [:input.input {:value value
               :on-change #(re-frame/dispatch [::events/update-form id (-> % .-target .-value)])
               :type "text"}]]))

(defn radio-input [id valor]
   [:input {:type "radio"
            :value valor
            :checked (= @(re-frame/subscribe [::subs/db-tipo]) valor)
            :on-change #(re-frame/dispatch [::events/update-db id (-> % .-target .-value)])
            }])

(defn main-panel []
  (let [tipo-ativo @(re-frame/subscribe [::subs/db-tipo])
        chave-formulario (case tipo-ativo  "CDB" [:id-participante
                                                  :cnpj-cpf
                                                  :tipo-de-regime
                                                  :data-de-emissao
                                                  :data-de-vecimento
                                                  :quantidade
                                                  :valor-unitario-de-emissao
                                                  :codigo-isin
                                                  :local-de-emissao
                                                  :emissao-municipio
                                                  :emissao-uf
                                                  :local-pagamento
                                                  :pagamento-municipio
                                                  :pagamento-uf
                                                  :condicao-resgate-antecipado
                                                  :vinculado
                                                  :forma-pagamento
                                                  :tipo-cdb
                                                  :curvas
                                                  :rentabilidade
                                                  :taxa-flutuante
                                                  :taxa-juros]  "" )
        is-valid? @(re-frame/subscribe [::subs/form-is-valid? chave-formulario])]
    [:div
     [:div
      [radio-input :tipo "CDB"]
      [radio-input :tipo "SWAP"]]
     [:div.formulario
      [:h1 {:class "titulo"} "Registro de CDB"]
      [:div.div-input
       [text-input :id-participante "ID Participante"]
       [text-input :cnpj-cpf "CNPJ/CPF"]]
      [:div.div-input
       [text-input :tipo-de-regime "Tipo de Regime"]
       [text-input :data-de-emissao "Data de Emissão"]
       [text-input :data-de-vecimento "Data de Vencimento"]]
      [:div.div-input
       [text-input :quantidade "Quantidade"]
       [text-input :valor-unitario-de-emissao "Valor Unitario de Emissão"]
       [text-input :codigo-isin "Codigo ISIN"]]
      [:div.div-input
       [text-input :local-de-emissao "Local de Emissão"]
       [text-input :emissao-municipio "Municipio"]
       [text-input :emissao-uf "UF"]]
      [:div.div-input
       [text-input :local-pagamento "Local de Pagamento"]
       [text-input :pagamento-municipio "Municipio"]
       [text-input :pagamento-uf "UF"]]
      [:div.div-input
       [text-input :condicao-resgate-antecipado "Condição de Resgate Antecipado"]
       [text-input :vinculado "Vinculado"]
       [text-input :forma-pagamento "Forma de pagamento"]]
      [:div.div-input
       [text-input :tipo-cdb "Tipo de CDB"]
       [text-input :curvas "Curvas"]]
      [:div.div-input
       [text-input :rentabilidade "Rentabilidade do indexador de taxa flutuante"]
       [text-input :taxa-flutuante "Taxa Flutuante"]
       [text-input :taxa-juros "Taxa de Juros"]]
      [:div.div-botao
       [:button.registrar {:disabled (not is-valid?)
                           :on-click #(re-frame/dispatch [::events/save-form])}
        "Registrar"]]]]))
