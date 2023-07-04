(ns mini-registradora.views
  (:require
   [re-frame.core :as re-frame]
   [mini-registradora.subs :as subs]
   [mini-registradora.events :as events]))

(def tipo-cdb ["Multiplas curvas"])
(def tipo-swap ["Taxa de juros"])

(defn text-input [id label tipo]
  (let [value @(re-frame/subscribe [::subs/form id])]
     [:div.div
      [:label.texto label]
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
    [text-input :id-participante-cdb "ID Participante" "text"]
    [text-input :conta-cnpj-cpf "CNPJ/CPF" "text"]]
   [:div.div-input
    [text-input :tipo-de-regime "Tipo de Regime" "text"]
    [text-input :data-de-emissao "Data de Emissão" "date"]
    [text-input :data-de-vecimento "Data de Vencimento" "date"]]
   [:div.div-input
    [text-input :valor-unitario-de-emissao "Valor Unitario de Emissão" "text"]
    [text-input :codigo-isin "Codigo ISIN" "text"]]
   [:div.div-input
    [text-input :local-de-emissao "Local de Emissão" "text"]
    [text-input :municipio-emissao "Municipio" "text"]
    [text-input :uf-local-emissao "UF" "text"]]
   [:div.div-input
    [text-input :local-pagamento "Local de Pagamento" "text"]
    [text-input :municipio-pagamento "Municipio" "text"]
    [text-input :uf-local-pagamento "UF" "text"]]
   [:div.div-input
    [text-input :cond-resgate-antecipado "Condição de Resgate Antecipado" "text"]
    [text-input :vinculado "Vinculado" "text"]
    [text-input :forma-pagamento "Forma de pagamento" "text"]]
   [:div.div-input
    (select-input :tipo-cdb "Tipo" tipo-cdb)
    (radio-input-curvas :multiplas-curvas "2" "3" "Multiplas Curvas")]
   [:div.div-input
    [text-input :rentabilidade "Rentabilidade do indexador de taxa flutuante" "text"]
    [text-input :taxa-flutuante "Taxa Flutuante" "text"]
    [text-input :taxa-juros "Taxa de Juros" "text"]]
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
    [text-input :data-de-inicio "Data Inicio" "date"]
    [text-input :data-de-vencimento "Data de Vencimento" "date"]]
   [:div.div-input
    [text-input :valor-base "Valor base" "text"]
    [text-input :adesao-contrato "Adesao contrato" "text"]]
   [:div.div-input
    [text-input :percentual-comprador "Percentual comprador" "text"]
    [text-input :categoria-comprador "Categoria comprador" "text"]
    [text-input :juros-comprador "Juros comprador" "text"]
    [text-input :curva-comprador "Curva comprador" "text"]]
   [:div.div-input
    [text-input :percentual-vendedor "Percentual vendedor" "text"]
    [text-input :categoria-vendedor "Categoria vendedor" "text"]
    [text-input :juros-vendedor "Juros vendedor" "text"]
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

(defn consult-dados []
  [:div.formulario
   [:div.div-titulo
    [:h1.titulo "Consulta dados"]]
   [:div.div-input
    [text-input :cnpj-participante "CNPJ do participante" "text"]
    [text-input :id-ativo "ID Ativo" "text"]
    [text-input :data-de-inicio "Data inicio" "date"]
    [text-input :data-de-vencimento "Data vencimento" "date"]]
   [:div.div-botao
    [:button.registrar {:on-click #(re-frame/dispatch [::events/save-form])} "Consultar"]]])

(defn main-panel []
  (let [tipo-ativo @(re-frame/subscribe [::subs/db-tipo])
        chave-formulario (case tipo-ativo
                           "CDB" [:id-participante-cdb
                                  :conta-cnpj-cpf
                                  :tipo-de-regime
                                  :data-de-emissao
                                  :data-de-vecimento
                                  :valor-unitario-de-emissao
                                  :codigo-isin
                                  :local-de-emissao
                                  :municipio-emissao
                                  :uf-local-emissao
                                  :local-pagamento
                                  :municipio-pagamento
                                  :uf-local-pagamento
                                  :cond-resgate-antecipado
                                  :vinculado
                                  :forma-pagamento
                                  :tipo-cdb
                                  :multiplas-curvas
                                  :rentabilidade
                                  :taxa-flutuante
                                  :taxa-juros]
                           "SWAP" [:id-participante-swap
                                   :tipo-swap
                                   :tipo-pagamento
                                   :cnpj-comprador
                                   :cnpj-vendedor
                                   :data-de-inicio
                                   :data-de-vencimento
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
                           "Participante" [:cnpj-participante
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
                           nil)
        is-valid? @(re-frame/subscribe [::subs/form-is-valid? chave-formulario])]
    [:div
     [:div.div-radio
      [:div.div-menu
       [:h1.titulo-menu "CDB"]
       [radio-input :tipo "CDB"]]
      [:div.div-menu
       [:h1.titulo-menu "SWAP"]
       [radio-input :tipo "SWAP"]]
      [:div.div-menu
       [:h1.titulo-menu "Participante"]
       [radio-input :tipo "Participante"]]
      [:div.div-menu
       [:h1.titulo-menu "Consulta"]
       [radio-input :tipo "Consulta"]]]

     (case tipo-ativo
       "CDB" (ativo-cdb is-valid?)
       "SWAP" (ativo-swap is-valid?)
       "Participante" (registro-partipante is-valid?)
       "Consulta" (consult-dados)
       [:h1.titulo-mini-registradora "Mini-Registradora"])]))
