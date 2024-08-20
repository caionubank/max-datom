;; Humano caio, muitas vezes será benéfico parametrizar as queries para permitir a reutilização com diferentes variáveis. Por exemplo, na query abaixo, $ está vinculado a db e ?worker-id está vinculado ao UUID. O $ foi implícito nas queries anteriores e é usualmente utilizado apenas ao fornecer entradas adicionais.
;; (d/q '[:find (pull ?e [:farm/_workers])
;;        :in $ ?worker-id
;;        :where [?e :worker/id ?worker-id]] db #uuid "DC122EEA-5D92-45CE-98ED-874AFA648CEE")
;; Modifique a query para usar o author-id como uma entrada para a query recuperar informações sobre o autor específico.

(ns level-8
  (:require
    [datomic.client.api :as d]
    [max-datom.connections :refer [db]]))

(def author-id #uuid "35636B79-EE46-4447-8AA7-3F0FB351C45C")

(d/q '[:find (pull ?e [:author/first-name :author/last-name])
       :in $ ?author-id
       :where [?e :author/id ?author-id]] db author-id)
