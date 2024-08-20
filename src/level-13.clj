;; Humano caio, você pode escrever suas própriasfunções customizadaspara uso como agregação, predicado ou cláusulas de função em queries.

Por exemplo, para retornar {:stats {:worker/age 27 :days-since-disciplinary-incident 2} no lugar do ref em :farm/top-worker no lugar do ref em
                            (defn worker-stats [db worker-eid]
                              (let [{:worker/keys [age days-since-disciplinary-incident]} 
                                    (d/pull db '[:worker/age :worker/days-since-disciplinary-incident] worker-eid)] 
                                {:worker/stats {:age age :days-since-disciplinary-incident days-since-disciplinary-incident}}))

                            (d/q '[:find (pull ?farm [:farm/name]) ?worker-stats
                                   :where [?farm :farm/top-worker ?top-worker]
                                   [(datomic-scratch/worker-stats $ ?top-worker) ?worker-stats]] db)
;;                             Usando uma função customizada, atualize a query para retornar o autor :user/first+last-name e {:post/stats (str "Likes: " <<postLikeCount>> " Dislikes: " <<postDislikeCount>>)}

(ns level-13
  (:require
    [datomic.client.api :as d]
    [max-datom.connections :refer [db]]))

(defn social-stats [db post]
  (let [{:post/keys [likes dislikes]} (d/pull db '[:post/likes :post/dislikes] post)]
        {:post/stats (str "Likes: " (count likes) " Dislikes: " (count dislikes))}))

(d/q '[:find  (pull ?posts [{:post/author [:user/first+last-name]}]) ?social-stats
       :where [?posts :post/author ?user]
       		    [?user :user/first+last-name]
              [(level-13/social-stats $ ?posts) ?social-stats]] db)

;; Ou assim, sem o bind
(d/q '[:find  (pull ?posts [{:post/author [:user/first+last-name]}]) ?social-stats
       :where [?posts :post/author ?user]
              [(level-13/social-stats $ ?posts) ?social-stats]] db)

;; output
[[{:post/author {:user/first+last-name ["E. L." "Mainframe"]}}
  {:post/stats "Likes: 0 Dislikes: 1"}]
 [{:post/author {:user/first+last-name ["E. L." "Mainframe"]}}
  {:post/stats "Likes: 1 Dislikes: 1"}]
 [{:post/author {:user/first+last-name ["E. L." "Mainframe"]}}
  {:post/stats "Likes: 1 Dislikes: 0"}]
 [{:post/author {:user/first+last-name ["Segfault" "Larsson"]}}
  {:post/stats "Likes: 1 Dislikes: 1"}]]
