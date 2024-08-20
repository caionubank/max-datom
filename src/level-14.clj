;; Precisamos acelerar o ritmo para prepará-lo. O desempenho da query é de extrema importância. As cláusulas :where das queries do Datomic são executadas em ordem. Para minimizar o trabalho realizado pelo mecanismo de queries, as cláusulas mais restritivas devem vir antes das cláusulas menos restritivas.

;; Por exemplo:
;; (d/q '[:find ?farm-name
;;        :in $ ?worker-id
;;        :where [?worker :worker/id ?worker-id]
;;               [?farm :farm/workers ?worker]
;;               [:?farm :farm/name ?farm-name]] db "123-abc")

(ns level-14
  (:require
   [datomic.client.api :as d]
   [max-datom.connections :refer [db]]))

(d/q '[:find  ?from
       :where [?transfer :transfer/id #uuid "4C5116AF-3B75-1FFE-AE5B-D81D8B1B251F"]
       [?transfer :transfer/from ?from]] db)

