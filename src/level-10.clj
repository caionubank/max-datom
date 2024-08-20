;Parabéns, humano caio! Você completou o programa de entrada para novos associados e agora é oficialmente um membro júnior do time Max Datom (TM). Eu tenho certeza que seus pais ficariam orgulhosos. Eu enviaria a eles a notícia da sua conquista se todas as comunicações de entrada e saída de fazendas de trabalho não fossem estritamente proibidas. Para comemorar seu novo status eu estou presenteando você com sua própria camiseta polo do Max Datom (TM). Nós entendemos que por alguma razão, camisetas polo fazem humanos se sentirem parte de um time. A camiseta é sua. O custo dela vai ser deduzido de seus futuros créditos ganhos.
;
;Chega de comemorações. Você ainda tem muito a aprender. Uma função agregada aparece em uma cláusula de busca e transforma um resultado.
;Funções agregadas podem receber variáveis, constantes ou src-vars como argumentos.
;
;Por exemplo, para retornar uma contagem de trabalhadores atribuídos a cada fazenda de energia junto com o nome da fazenda, a seguinte query poderia ser feita;
;(d/q '[:find (count ?workers) ?farm-name
;       :where [?e :farm/name ?farm-name]
;       [?e :farm/workers ?workers]] db)
;Depois de inspecionar o novo schema do conjunto de dados, atualize a query para retornar o :user/first+last-name e contagem de :post/author para o usuário associado com :user/id #uuid "1B341635-BE22-4ACC-AE5B-D81D8B1B7678"

(ns level-10
    (:require
     [datomic.client.api :as d]
     [max-datom.connections :refer [db]]))

(d/q '[:find  ?user-name (count ?post)
       :where [?user :user/id #uuid "1B341635-BE22-4ACC-AE5B-D81D8B1B7678"]
              [?user :user/first+last-name ?user-name] 
              [?post :post/author ?user]] db)
