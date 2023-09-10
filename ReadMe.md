# Implementação Produtor-Consumidor

Este trabalho é individual. Você deve implementar a resolução do problema Produtor-Consumidor com semáforos. Além da implementação, você deve responder à seguinte pergunta: podemos trocar a ordem das chamadas de wait dos semáforos mutex e empty/full? O que irá acontecer se fizermos a troca?

A submissão do trabalho deve ser um arquivo contendo o código. Além disso, vocês devem submeter o link para um vídeo mostrando a execução do código.

Resposta para: **podemos trocar a ordem das chamadas de wait dos semáforos mutex e empty/full? O que irá acontecer se fizermos a troca?**

Não, não podemos trocar a ordem das chamadas de espera para o mutex e os semáforos vazios/cheios. Se o fizermos, então a thread produtora poderá adicionar um item ao buffer mesmo se o buffer estiver cheio.

A razão para isso é que o semáforo mutex é usado para proteger a seção crítica do código, que é a parte do código onde o buffer é acessado. Se a thread produtora adquirir o semáforo mutex antes do semáforo vazio, então a thread produtora será capaz de acessar o buffer mesmo se ele estiver cheio. Isso ocorre porque o semáforo mutex impedirá que a thread consumidora remova um item do buffer, mas a thread produtora ainda poderá adicionar um item ao buffer.

Portanto, a ordem das chamadas de espera deve ser semáforo vazio, semáforo mutex e semáforo cheio.

