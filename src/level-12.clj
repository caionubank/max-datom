;; Humano caio, a :xform opção dá a habilidade de transformar o valor retornado pelo pull por um atributo.

;; Por exemplo, ao invés de um número inteiro para "expired batteries" associadas a um determinado parque de energia, é possível usar o requisito :xform para retornar uma palavra.
;; (defn int->str [i]
;;   (case i
;;         0 "zero"
;;         1 "one"
;;         2 "two"
;;         3 "three"
;;         "Fui aconselhado a não falar números"))

;; (d/q '[:find  (pull ?farm [:farm/name [:farm/expired-battery-count :xform ns/int->str]])
;;        :where [?farm :farm/name]]
;;      db)
;; Usando :xform atualize a query para retornar para o :user/first+last-name do autor da postagem e uma string na forma de Comment Count: <count> para os :post/comments

(ns level-12
  (:require
   [datomic.client.api :as d]
   [max-datom.connections :refer [db]]))

(defn comment-count-str [x]
  (str "Comment Count: " (count x)))

(d/q '[:find  (pull ?posts [{:post/author [:user/first+last-name]}
                            [:post/comments :xform level-12/comment-count-str]])
       :where [?posts :post/author _] 
              [?posts :post/comments]] db)
