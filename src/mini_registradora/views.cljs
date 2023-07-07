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
           :on-change #(re-frame/dispatch [::events/update-db id (-> % .-target .-value)])}])

(defn radio-input-consulta [id valor]
  [:input {:type "radio"
           :value valor
           :checked (= @(re-frame/subscribe [::subs/db-tipo-consulta]) valor)
           :on-change #(re-frame/dispatch [::events/update-consulta id (-> % .-target .-value)])}])

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
    (select-input :tipo "Tipo" tipo-cdb)
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
    [text-input :id_participante "ID Participante" "text"]
    (select-input :tipo "Tipo" tipo-swap)
    [text-input :tipo_pagamento "Tipo pagamento" "text"]]
   [:div.div-input
    [text-input :cnpj_comprador "CNPJ comprador" "text"]
    [text-input :cnpj_vendedor "CNPJ vendedor" "text"]
    [text-input :data_inicio "Data Inicio" "date"]
    [text-input :data_vencimento "Data de Vencimento" "date"]]
   [:div.div-input
    [text-input :valor_base "Valor base" "double"]
    [text-input :adesao_contrato "Adesao contrato" "text"]]
   [:div.div-input
    [text-input :percentual_comprador "Percentual comprador" "double"]
    [text-input :categoria_comprador "Categoria comprador" "text"]
    [text-input :juros_comprador "Juros comprador" "double"]
    [text-input :curva_comprador "Curva comprador" "text"]]
   [:div.div-input
    [text-input :percentual_vendedor "Percentual vendedor" "double"]
    [text-input :categoria_vendedor "Categoria vendedor" "text"]
    [text-input :juros_vendedor "Juros vendedor" "double"]
    [text-input :curva_vendedor "Curva vendedor" "text"]]
   [:div.div-input
    [text-input :caracteristicas_contrato "Caracteristicas do contrato" "text"]]
   [:div.div-botao
    [:button.registrar {:disabled (not is-valid?)
                        :on-click #(re-frame/dispatch [::events/save-form])}
     "Registrar"]]])

(defn registro-partipante [is-valid?]
  [:div.formulario
   [:div.div-titulo
    [:h1.titulo "Registro do Participante"]]
   [:div.div-input
    [text-input :cnpj "CNPJ" "text"]
    [text-input :tipo_de_instituicao "Tipo de Instituição" "text"]
    [text-input :setor_area "Setor Area" "text"]]
   [:div.div-input
    [text-input :razao_social "Razão Social" "text"]
    [text-input :nome_fantasia "Nome fantisia" "text"]
    [text-input :codigo_agregador "Codigo Agregador" "text"]]
   [:div.div-input
    [text-input :controle_acionario "Controle acionario" "text"]
    [text-input :origem_do_capital "Origem do Capital" "text"]
    [text-input :isencao_inscr_estadual "Isenção inscritura Estadual" "text"]]
   [:div.div-input
    [text-input :num_inscr_estadual "Inscrição Estadual" "text"]
    [text-input :isencao_inscr_municipal "Isenção inscritura Municipal" "text"]
    [text-input :num_inscr_municipal "Inscrição Municipal" "text"]]
   [:div.div-input
    [text-input :grupo_economico "Grupo Economico" "text"]
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
  (let [db-consulta @(re-frame/subscribe [::subs/db-consulta])]
    [:table.tabela
     [:thead
      [:tr
       (for [x db-consulta]
         [:th.linha-coluna.coluna {:stylr {:text-align (:alignment x)}}])]]
     [:tbody
      [:tr
       (for [x db-consulta]
         [:td.linha-coluna.linha {:stylr {:text-align (:alignment x)}}])]]]))

(defn consulta-participante [is-valid?]
  [:div.test
   [:div.div-titulo
    [:h1.titulo "Consulta Participante"]]
   [text-input :cnpj "CNPJ" "text"]
   [:div.div-botao
    [:button.registrar {:disabled (not is-valid?)
                        :on-click #(re-frame/dispatch [::events/consulta-form])} "Consultar"]
    (tabela-consulta)]])

(defn consulta-swap [is-valid?]
  [:div.test
   [:div.div-titulo
    [:h1.titulo "Consulta Swap"]]
   [text-input :id_participante "ID participante" "text"]
   [text-input :data_inicio "Data inicio" "date"]
   [text-input :data_vencimento "Data vencimento" "date"]
   [text-input :id_ativo "ID Ativo" "text"]
   [:div.div-botao
    [:button.registrar {:disabled (not is-valid?)
                        :on-click #(re-frame/dispatch [::events/consulta-form])} "Consultar"]
    (tabela-consulta)]])

(defn consulta-cdb [is-valid?]
  [:div.test
   [:div.div-titulo
    [:h1.titulo "Consulta CDB"]]
   [text-input :id_participante "ID participante" "text"]
   [text-input :data_emissao "Data Emisão" "date"]
   [text-input :id_ativo "ID Ativo" "text"]
   [:div.div-botao
    [:button.registrar {:disabled (not is-valid?)
                        :on-click #(re-frame/dispatch [::events/consulta-form])} "Consultar"]
    (tabela-consulta)]])

(defn consult-dados [is-valid?]
  (let [tipo-ativo @(re-frame/subscribe [::subs/db-tipo-consulta])]
    [:div
     [:div.formulario
      [:div.div-radio
       [:div.div-menu
        [:h1.titulo-menu "CDB"]
        [radio-input-consulta  :tela-consulta "cdb"]]
       [:div.div-menu
        [:h1.titulo-menu "SWAP"]
        [radio-input-consulta  :tela-consulta "swap"]]
       [:div.div-menu
        [:h1.titulo-menu "Participante"]
        [radio-input-consulta  :tela-consulta "participante"]]]
      (case tipo-ativo
        "participante" (consulta-participante is-valid?)
        "swap" (consulta-swap is-valid?)
        "cdb" (consulta-cdb is-valid?)
        nil)]]))

(defn main-panel []
  (let [tipo-ativo @(re-frame/subscribe [::subs/db-tipo])
        tipo-consulta @(re-frame/subscribe [::subs/db-tipo-consulta])
        chave-consulta (case tipo-consulta
                         "participante" [:cnpj]
                         "swap" [:id_participante
                                 :data_inicio
                                 :data_vencimento
                                 :id_ativo]
                         "cdb" [:id_participante
                                :data_emissao
                                :id_ativo]
                         nil)
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
                                  :tipo
                                  :multiplas_curvas
                                  :rentabilidade_ind_tx_flut
                                  :taxa_flutuante
                                  :taxa_juros_spread]
                           "swap" [:id_participante
                                   :tipo
                                   :tipo_pagamento
                                   :cnpj_comprador
                                   :cnpj_vendedor
                                   :data_inicio
                                   :data_vencimento
                                   :valor_base
                                   :adesao_contrato
                                   :percentual_comprador
                                   :categoria_comprador
                                   :juros_comprador
                                   :curva_comprador
                                   :percentual_vendedor
                                   :categoria_vendedor
                                   :juros_vendedor
                                   :curva_vendedor
                                   :caracteristicas_contrato]
                           "participante" [:cnpj
                                           :tipo_de_instituicao
                                           :setor_area
                                           :razao_social
                                           :nome_fantasia
                                           :codigo_agregador
                                           :controle_acionario
                                           :origem_do_capital
                                           :isencao_inscr_estadual
                                           :num_inscr_estadual
                                           :isencao_inscr_municipal
                                           :num_inscr_municipal
                                           :grupo_economico
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
                           nil)
        is-valid? @(re-frame/subscribe [::subs/form-is-valid? chave-formulario])
        is-valid-consulta? @(re-frame/subscribe [::subs/form-is-valid? chave-consulta])]
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

     (case tipo-ativo
       "cdb" (ativo-cdb is-valid?)
       "swap" (ativo-swap is-valid?)
       "participante" (registro-partipante is-valid?)
       "consulta" (consult-dados is-valid-consulta?)
       [:h1.titulo-mini-registradora "Mini-Registradora"])]))
