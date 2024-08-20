;; Humanocaio, a transação pode parecer normal agora, mas há uma razão pela qual os fundos não chegam ao destinatário pretendido. Ao alavancar a uma history database você pode fazer uma query para consultar todas as afirmações e retratações independentemente de quando elas ocorreram.

;; Por exemplo
;; (d/q '[:find ?e ?v ?tx ?op
;;        :in $ ?worker-id
;;        :where [?e :worker/id ?worker-id]
;;        [?e :worker/rating ?v ?tx ?op]] (d/history db) "123-abc")
;; A consulta acima retornará todas as entidades, valores, transações e operações para quaisquer afirmações ou retrações para o atributo :work/rating da entidade associada a :worker/id "123-abc"

;; Use o histórico db para fazer uma query e consultar e, v, t e op para o atributo :transfer/to associado com :transfer/id #uuid "59B9C791-74CE-4C51-A4BC-EF6D06BEE2DBA"

(ns level-16
  (:require
   [datomic.client.api :as d]
   [max-datom.connections :refer [db]]))

(def history-db (d/history db))
(def transfer-id #uuid "59B9C791-74CE-4C51-A4BC-EF6D06BEE2DBA")

(d/q '[:find  ?transfer ?v ?t ?op
       :in $ ?transfer-id
       :where [?transfer :transfer/id ?transfer-id]
       [?transfer :transfer/to ?v ?t ?op]] history-db transfer-id)

;; OUTPUT

[[92358976733273 92358976733270 13194139533319 true]
 [92358976733273 92358976733271 13194139533320 true]
 [92358976733273 92358976733270 13194139533320 false]]
