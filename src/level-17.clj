;; Humano caio, vale a pena investigar o valor :transfer/to que parece ter sido alterado. Usando um as-of a base de dados vai nos permitir fazer uma query em um ponto específico no tempo. O ponto no tempo pode ser especificado tanto como instant ou como transaction id.

;; Por exemplo:
;; @(def db-before (d/as-of (d/db conn) 23364758362548))

;;  (d/q '[:find ?v 
;;         :in $ ?worker-id
;;         :where [?e :worker/id ?worker-id]
;;                [?e :worker/rating ?v]] db-before "123-abc")
;; A query acima vai retornar o valor para :worker/rating no ponto específico no tempo correspondendo à transaction id 23364758362548.

;; Puxe todos os dados incluindo o :account/owner para:transfer/id #uuid "59b9c791-74ce-4c51-a4bc-ef6d06bee2db" como de transaction id 13194139533319.

;; Finalmente a prova que precisamos! A Silicone Family parece estar usando suas próprias contas para o destino das transferências. Depois que o dinheiro é movido, eles alteram a transferência para parecer que deveria ter ido para o destinatário correto. Simples mas efetivo. Bom trabalho caio, vamos cuidar deste caso a partir daqui.

(ns level-17
  (:require
   [datomic.client.api :as d]
   [max-datom.connections :refer [db]]))

@(def db-as-of (d/as-of db 13194139533319))

(def transfer-id #uuid "59B9C791-74CE-4C51-A4BC-EF6D06BEE2DB")

(d/q '[:find  (pull ?to [* {:account/owner [*]}])
       :in $ ?transfer-id
       :where [?transfer :transfer/id ?transfer-id]
       [?transfer :transfer/to ?to]] db-as-of transfer-id)
