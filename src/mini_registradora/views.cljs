(ns mini-registradora.views
  (:require
   [re-frame.core :as re-frame]
   [mini-registradora.subs :as subs]
   [mini-registradora.events :as events]))

(def tipo-cdb ["Multiplas curvas"])
(def tipo-swap ["Taxa de juros"])

(defn text-input [id texto tipo]
  (let [value @(re-frame/subscribe [::subs/form id])]
     [:div.div
      [:label.texto texto]
      [:input.input {:value value
               :on-change #(re-frame/dispatch [::events/update-form id (-> % .-target .-value)])
               :type tipo}]]))

(defn radio-input [id valor]
   [:input {:type "radio"
            :value valor
            :checked (= @(re-frame/subscribe [::subs/db-tipo]) valor)
            :on-change #(re-frame/dispatch [::events/update-db id (-> % .-target .-value)]) }])

(defn radio-input-curvas [id valor1 valor2 texto]
  [:div.div
   [:label.texto texto]
   [:div.div-colum
    [:label valor1]
    [:input {:type "radio"
             :value valor1
             :checked (= @(re-frame/subscribe [::subs/form-curvas]) valor1)
             :on-change #(re-frame/dispatch [::events/update-form id (-> % .-target .-value)])}]]
   [:div.div-colum
    [:label valor2]
    [:input {:type "radio"
             :value valor2
             :checked (= @(re-frame/subscribe [::subs/form-curvas]) valor2)
             :on-change #(re-frame/dispatch [::events/update-form id (-> % .-target .-value)])}]]])

(defn select-input [id  texto opcoes]
  (let [value @(re-frame/subscribe [::subs/form id])]
    [:div.div
     [:label.texto texto]
      [:select.form-control {:value value :on-change #(re-frame/dispatch [::events/update-form id (-> % .-target .-value)])}
       [:option {:value ""} "Selecione o tipo"]
       (map (fn [o] [:option {:key o :value o} o]) opcoes)]]))

(defn ativo-cdb [is-valid?]
  [:div.formulario
   [:div.div-titulo
    [:h1.titulo "Registro de CDB"]]
   [:div.div-input
    [text-input :id_participante "ID Participante" "text"]
    [text-input :conta_cpf_cnpj "CNPJ/CPF" "text"]]
   [:div.div-input
    [text-input :tipo_regime "Tipo de Regime" "text"]
    [text-input :data_emissao "Data de Emissão" "date"]
    [text-input  :data_vencimento "Data de Vencimento" "date"]]
   [:div.div-input
    [text-input :valor_unitario_emissao "Valor Unitario de Emissão" "double"]
    [text-input :codigo_isin "Codigo ISIN" "text"]]
   [:div.div-input
    [text-input :local_emissao "Local de Emissão" "text"]
    [text-input :municipio_emissao "Municipio" "text"]
    [text-input :uf_local_emissao "UF" "text"]]
   [:div.div-input
    [text-input :local_pagamento "Local de Pagamento" "text"]
    [text-input :municipio_pagamento "Municipio" "text"]
    [text-input :uf_local_pagamento "UF" "text"]]
   [:div.div-input
    [text-input :cond_resgate_antecipado "Condição de Resgate Antecipado" "text"]
    [text-input :vinculado "Vinculado" "boolean"]
    [text-input  :forma_pagamento "Forma de pagamento" "text"]]
   [:div.div-input
    (select-input :tipo-cdb "Tipo" tipo-cdb)
    (radio-input-curvas :multiplas_curvas "2" "3" "Multiplas Curvas")]
   [:div.div-input
    [text-input :rentabilidade_ind_tx_flut "Rentabilidade do indexador de taxa flutuante" "text"]
    [text-input :taxa_flutuante "Taxa Flutuante" "double"]
    [text-input :taxa_juros_spread "Taxa de Juros" "double"]]
   [:div.div-botao
    [:button.registrar {:disabled (not is-valid?)
                        :on-click #(re-frame/dispatch [::events/save-form])}
     "Registrar"]]])

(defn ativo-swap [is-valid?]
  [:div.formulario
   [:div.div-titulo
    [:h1.titulo "Registro de SWAP"]]
   [:div.div-input
    [text-input :id-participante-swap "ID Participante" "text"]
    (select-input :tipo-swap "Tipo" tipo-swap)
    [text-input :tipo-pagamento "Tipo pagamento" "text"]]
   [:div.div-input
    [text-input :cnpj-comprador "CNPJ comprador" "text"]
    [text-input :cnpj-vendedor "CNPJ vendedor" "text"]
    [text-input :data-inicio "Data Inicio" "date"]
    [text-input :data-vencimento "Data de Vencimento" "date"]]
   [:div.div-input
    [text-input :valor-base "Valor base" "double"]
    [text-input :adesao-contrato "Adesao contrato" "text"]]
   [:div.div-input
    [text-input :percentual-comprador "Percentual comprador" "double"]
    [text-input :categoria-comprador "Categoria comprador" "text"]
    [text-input :juros-comprador "Juros comprador" "double"]
    [text-input :curva-comprador "Curva comprador" "text"]]
   [:div.div-input
    [text-input :percentual-vendedor "Percentual vendedor" "double"]
    [text-input :categoria-vendedor "Categoria vendedor" "text"]
    [text-input :juros-vendedor "Juros vendedor" "double"]
    [text-input :curva-vendedor "Curva vendedor" "text"]]
   [:div.div-input
    [text-input :caracteristicas-contrato "Caracteristicas do contrato" "text"]]
   [:div.div-botao
    [:button.registrar {:disabled (not is-valid?)
                        :on-click #(re-frame/dispatch [::events/save-form])}
     "Registrar"]]])

(defn registro-partipante [is-valid?]
  [:div.formulario
   [:div.div-titulo
    [:h1.titulo "Registro do Participante"]]
    [:div.div-input
     [text-input :cnpj-participante "CNPJ" "text"]
     [text-input :tipo-de-instituicao "Tipo de Instituição" "text"]
     [text-input :setor-area "Setor Area" "text"]]
   [:div.div-input
    [text-input :razao-social "Razão Social" "text"]
    [text-input :nome-fantasia "Nome fantisia" "text"]
    [text-input :codigo-agregador "Codigo Agregador" "text"]]
   [:div.div-input
    [text-input :controle-acionario "Controle acionario" "text"]
    [text-input :origem-do-capital "Origem do Capital" "text"]
    [text-input :isencao-inscr-estadual "Isenção inscritura Estadual" "text"]]
   [:div.div-input
    [text-input :num-inscr-estadual "Inscrição Estadual" "text"]
    [text-input :isencao-inscr-municipal "Isenção inscritura Municipal" "text"]
    [text-input :num-inscr-municipal "Inscrição Municipal" "text"]]
   [:div.div-input
    [text-input :grupo-economico "Grupo Economico" "text"]
    [text-input :email "E-mail" "email"]
    [text-input :telefone "Telefone" "tel"]
    [text-input :ramal "Ramal" "number"]]
   [:div.div-input
    [text-input :logradouro "Logradouro" "text"]
    [text-input :numero "Numero" "text"]
    [text-input :complemento "Complemento" "text"]]
   [:div.div-input
    [text-input :bairro "Bairro" "text"]
    [text-input :municipio "Municipio" "text"]
    [text-input :cep "CEP" "text"]
    [text-input :uf "UF" "text"]
    [text-input :pais "Pais" "text"]]
   [:div.div-botao
    [:button.registrar {:disabled (not is-valid?)
                        :on-click #(re-frame/dispatch [::events/save-form])}
     "Registrar"]]])

(defn tabela-consulta []
  (let [dados [["Item 1" 10]
               ["Item 2" 20]
               ["Item 3" 15]]]
    [:table.tabela
     [:thead
      [:tr
       [:th.linha-coluna.coluna "CNPJ"]
       [:th.linha-coluna.coluna "ID ativo"]
       [:th.linha-coluna.coluna "Data de inicio"]
       [:th.linha-coluna.coluna "Data do vencimento"]]]
     [:tbody
      (for [item dados]
        [:tr
         [:td.linha-coluna.linha (first item)]
         [:td.linha-coluna.linha (second item)]])]]))

(defn consult-dados [is-valid?]
  [:div
  [:div.formulario
   [:div.div-titulo
    [:h1.titulo "Consulta"]]
   [text-input :cnpj-consulta "CNPJ" "text"]
   [:div.div-botao
    [:button.registrar {:disabled (not is-valid?)
                        :on-click #(re-frame/dispatch [::events/consulta-form])} "Consultar"]]]
   (tabela-consulta)
   ])

(defn main-panel []
  (let [tipo-ativo @(re-frame/subscribe [::subs/db-tipo])
        chave-formulario (case tipo-ativo
                           "cdb" [:id_participante
                                  :conta_cpf_cnpj
                                  :tipo_regime
                                  :data_emissao
                                  :data_vencimento
                                  :valor_unitario_emissao
                                  :codigo_isin
                                  :local_emissao
                                  :municipio_emissao
                                  :uf_local_emissao
                                  :local_pagamento
                                  :municipio_pagamento
                                  :uf_local_pagamento
                                  :cond_resgate_antecipado
                                  :vinculado
                                  :forma_pagamento
                                  :tipo-cdb
                                  :multiplas_curvas
                                  :rentabilidade_ind_tx_flut
                                  :taxa_flutuante
                                  :taxa_juros_spread]
                           "swap" [:id-participante-swap
                                   :tipo-swap
                                   :tipo-pagamento
                                   :cnpj-comprador
                                   :cnpj-vendedor
                                   :data-inicio
                                   :data-vencimento
                                   :valor-base
                                   :adesao-contrato
                                   :percentual-comprador
                                   :categoria-comprador
                                   :juros-comprador
                                   :curva-comprador
                                   :percentual-vendedor
                                   :categoria-vendedor
                                   :juros-vendedor
                                   :curva-vendedor
                                   :caracteristicas-contrato]
                           "participante" [:cnpj-participante
                                           :tipo-de-instituicao
                                           :setor-area
                                           :razao-social
                                           :nome-fantasia
                                           :codigo-agregador
                                           :controle-acionario
                                           :origem-do-capital
                                           :isencao-inscr-estadual
                                           :num-inscr-estadual
                                           :isencao-inscr-municipal
                                           :num-inscr-municipal
                                           :grupo-economico
                                           :email
                                           :telefone
                                           :ramal
                                           :logradouro
                                           :numero
                                           :complemento
                                           :bairro
                                           :municipio
                                           :cep
                                           :uf
                                           :pais]
                           "consulta" [:cnpj-consulta]
                           nil)
        is-valid? @(re-frame/subscribe [::subs/form-is-valid? chave-formulario])]
    [:div
     [:div.div-radio
      [:div.div-menu
       [:h1.titulo-menu "CDB"]
       [radio-input :tipo-tela "cdb"]]
      [:div.div-menu
       [:h1.titulo-menu "SWAP"]
       [radio-input :tipo-tela "swap"]]
      [:div.div-menu
       [:h1.titulo-menu "Participante"]
       [radio-input :tipo-tela "participante"]]
      [:div.div-menu
       [:h1.titulo-menu "Consulta"]
       [radio-input :tipo-tela "consulta"]]]

     (prn @(re-frame/subscribe [::subs/db-consulta]))

     (case tipo-ativo
       "cdb" (ativo-cdb is-valid?)
       "swap" (ativo-swap is-valid?)
       "participante" (registro-partipante is-valid?)
       "consulta" (consult-dados is-valid?)
       [:h1.titulo-mini-registradora "Mini-Registradora"])]))
