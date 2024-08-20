;; Humano caio, nós temos razões para acreditar que a Silicone Crime Family está fazendo uma transação corrupta de criptomoeda. Há inúmeros relatórios de requisições de transações quais o dinheiro é removido de uma conta mas nunca chega ao destinatário pretendido. Nós precisamos reunir evidências concretas de atividade criminal.

;; Puxe todos os dados de :transfer/from e :transfer/to incluindo :account/owner para uma transação reportada recentemente :transfer/id #uuid "59B9C791-74CE-4C51-A4BC-EF6D06BEE2DBA"


;; Essa transferência parece normal, mas por que os fundos não chegaram ao Sr. CD? Continue até o nível 16 para investigar mais.

(ns level-15
  (:require
   [datomic.client.api :as d]
   [max-datom.connections :refer [db]]))

(def transfer-id #uuid "59B9C791-74CE-4C51-A4BC-EF6D06BEE2DBA")

(d/q '[:find (pull ?transfer [* {:transfer/from [* {:account/owner [*]}]
                                 :transfer/to [* {:account/owner [*]}]}])
       :in $ ?transfer-id
       :where [?transfer :transfer/id ?transfer-id]
       [?transfer :transfer/from ?from]
       [?from :account/owner ?acc-from]
       [?transfer :transfer/to ?to]
       [?to :account/owner ?acc-to]] db transfer-id)

;; OUTPUT
[[{:db/id 92358976733273,
   :transfer/from
   {:db/id 92358976733272,
    :account/balance 8900,
    :account/id #uuid "5164b8da-2fe4-41da-a5fd-1a697be1d2dd",
    :account/owner
    {:db/id 92358976733269,
     :user/first-name "Sonny",
     :user/id #uuid "afb83133-3a2e-40ce-91f8-2de4f61361de",
     :user/last-name "Diskon"}},
   :transfer/id #uuid "59b9c791-74ce-4c51-a4bc-ef6d06bee2db",
   :transfer/to
   {:db/id 92358976733271,
    :account/balance 2300,
    :account/id #uuid "d381dc80-c582-45eb-89e9-f6e188a71a29",
    :account/owner
    {:db/id 92358976733268,
     :user/first-name "Muhammad",
     :user/id #uuid "bfe00de4-bc19-4395-ba3b-2384ecf1a569",
     :user/last-name "CD"}},
   :transfer/amount 1000}]]
