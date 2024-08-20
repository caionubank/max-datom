;; Humano caio, com não cláusulas (not clauses), você pode expressar que uma ou mais variáveis lógicas dentro de uma query não devem satisfazer todo um conjunto de predicados.

;; Por exemplo, para retornar o nome de todas as fazendas de energia que não reportaram nenhuma fuga de funcionários, faça a query para entidades onde não há valor para o atributo :farm/escaped.
;; (d/q '[:find ?farm-name
;;        :where [?e :farm/name ?farm-name]
;;        (not [?e :farm/escaped])] db)
;; Atualize a query para retornar a conta de postagens de usuários e autores das postagens first+last-name apenas se a postagem não tiver nenhum :post/dislikes

(ns level-11
  (:require
   [datomic.client.api :as d]
   [max-datom.connections :refer [db]]))

(d/q '[:find  (count ?post) ?user-name
       :where [?user :user/first+last-name ?user-name]
       [?post :post/author ?user]
       (not [?post :post/dislikes])] db)
