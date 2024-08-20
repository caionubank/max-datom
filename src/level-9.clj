;; Humano caio, uma Collection Binding vincula uma única variável a vários valores passados como uma coleção. Usando a [variable ...] sintaxe isso pode ser usado para fazer "or" perguntas, como: Qual é a capacidade e o endereço associado a fazendas chamadas 'Power From the people' ou 'The Big Wheel'?
;; (d/q '[:find (pull ?e [:farm/capacity :farm/address])
;;        :in $ [?farm-name ...]
;;        :where [?e :farm/name ?farm-name]] db ["Power From the People" "The Big Wheel"])
;; Modifique a query para usar os author-ids como uma entrada para a query, a fim de recuperar os primeiros e últimos nomes de ambos os autores.

(ns level-9
  (:require
    [datomic.client.api :as d]
    [max-datom.connections :refer [db]]))

(def author-ids [#uuid "0955EDF7-FF8F-4EC2-AFB2-380E7E5D48D7"
                 #uuid "B7761785-79F9-49FA-97AF-13B4F5C2BCC2"])

;; Os '...' não é meramente ilustrativo, ele representa o rest do array. 

(d/q '[:find (pull ?e [:author/first-name :author/last-name])
       :in $ [?author-ids ...]
       :where [?e :author/id ?author-ids]] db author-ids)
