(ns mini-registradora.views
  (:require
   [re-frame.core :as re-frame]
   [mini-registradora.subs :as subs]
   [mini-registradora.events :as events]))

(def tipo-cdb ["Multiplas curvas"])
(def tipo-swap ["Taxa de juros"])

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
   [:h1.titulo "Registro de CDB"]
   [:div.div-input
    [text-input :id-participante-cdb "ID Participante"]
    [text-input :conta-cnpj-cpf "CNPJ/CPF"]]
   [:div.div-input
    [text-input :tipo-de-regime "Tipo de Regime"]
    [text-input :data-de-emissao "Data de Emissão"]
    [text-input :data-de-vecimento "Data de Vencimento"]]
   [:div.div-input
    [text-input :valor-unitario-de-emissao "Valor Unitario de Emissão"]
    [text-input :codigo-isin "Codigo ISIN"]]
   [:div.div-input
    [text-input :local-de-emissao "Local de Emissão"]
    [text-input :municipio-emissao "Municipio"]
    [text-input :uf-local-emissao "UF"]]
   [:div.div-input
    [text-input :local-pagamento "Local de Pagamento"]
    [text-input :municipio-pagamento "Municipio"]
    [text-input :uf-local-pagamento "UF"]]
   [:div.div-input
    [text-input :cond-resgate-antecipado "Condição de Resgate Antecipado"]
    [text-input :vinculado "Vinculado"]
    [text-input :forma-pagamento "Forma de pagamento"]]
   [:div.div-input
    (select-input :tipo-cdb "Tipo" tipo-cdb)
    (radio-input-curvas :multiplas-curvas "2" "3" "Multiplas Curvas")]
   [:div.div-input
    [text-input :rentabilidade "Rentabilidade do indexador de taxa flutuante"]
    [text-input :taxa-flutuante "Taxa Flutuante"]
    [text-input :taxa-juros "Taxa de Juros"]]
   [:div.div-botao
    [:button.registrar {:disabled (not is-valid?)
                        :on-click #(re-frame/dispatch [::events/save-form])}
     "Registrar"]]])

(defn ativo-swap [is-valid?]
  [:div.formulario
   [:h1.titulo "Registro de SWAP"]
   [:div.div-input
    [text-input :id-participante-swap "ID Participante"]
    (select-input :tipo-swap "Tipo" tipo-swap)
    [text-input :tipo-pagamento "Tipo pagamento"]]
   [:div.div-input
    [text-input :cnpj-comprador "CNPJ comprador"]
    [text-input :cnpj-vendedor "CNPJ vendedor"]
    [text-input :data-de-inicio "Data Inicio"]
    [text-input :data-de-vencimento "Data de Vencimento"]]
   [:div.div-input
    [text-input :valor-base "Valor base"]
    [text-input :adesao-contrato "Adesao contrato"]]
   [:div.div-input
    [text-input :percentual-comprador "Percentual comprador"]
    [text-input :categoria-comprador "Categoria comprador"]
    [text-input :juros-comprador "Juros comprador"]
    [text-input :curva-comprador "Curva comprador"]]
   [:div.div-input
    [text-input :percentual-vendedor "Percentual vendedor"]
    [text-input :categoria-vendedor "Categoria vendedor"]
    [text-input :juros-vendedor "Juros vendedor"]
    [text-input :curva-vendedor "Curva vendedor"]]
   [:div.div-input
    [text-input :caracteristicas-contrato "Caracteristicas do contrato"]]
   [:div.div-botao
    [:button.registrar {:disabled (not is-valid?)
                        :on-click #(re-frame/dispatch [::events/save-form])}
     "Registrar"]]])

(defn registro-partipante [is-valid?]
  [:div.formulario
   [:h1.titulo "Registro do Participante"]
    [:div.div-input
     [text-input :cnpj-participante "CNPJ"]
     [text-input :tipo-de-instituicao "Tipo de Instituição"]
     [text-input :setor-area "Setor Area"]]
   [:div.div-input
    [text-input :razao-social "Razão Social"]
    [text-input :nome-fantasia "Nome fantisia"]
    [text-input :codigo-agregador "Codigo Agregador"]]
   [:div.div-input
    [text-input :controle-acionario "Controle acionario"]
    [text-input :origem-do-capital "Origem do Capital"]
    [text-input :isencao-inscr-estadual "Isenção inscritura Estadual"]]
   [:div.div-input
    [text-input :num-inscr-estadual "Numero inscritura Estadual"]
    [text-input :isencao-inscr-municipal "Isenção inscritura Municipal"]
    [text-input :num-inscr-municipal "Numero inscritur Municipal"]]
   [:div.div-input
    [text-input :grupo-economico "Grupo Economico"]
    [text-input :email "Email"]
    [text-input :telefone "Telefone"]
    [text-input :ramal "Ramal"]]
   [:div.div-input
    [text-input :logradouro "Logradouro"]
    [text-input :numero "Numero"]
    [text-input :complemento "Complemento"]]
   [:div.div-input
    [text-input :bairro "Bairro"]
    [text-input :municipio "Municipio"]
    [text-input :cep "CEP"]
    [text-input :uf "UF"]
    [text-input :pais "Pais"]]
   [:div.div-botao
    [:button.registrar {:disabled (not is-valid?)
                        :on-click #(re-frame/dispatch [::events/save-form])}
     "Registrar"]]])

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
      [:h1.titulo1 "CDB"]
      [radio-input :tipo "CDB"]
      [:h1.titulo1 "SWAP"]
      [radio-input :tipo "SWAP"]
      [:h1.titulo1 "Participante"]
      [radio-input :tipo "Participante"]]

     (case tipo-ativo
       "CDB" (ativo-cdb is-valid?)
       "SWAP" (ativo-swap is-valid?)
       "Participante" (registro-partipante is-valid?)
       [:h1.titulo-mini-registradora "Mini-Registradora"])]))
