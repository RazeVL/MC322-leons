# MC322-leons

Atividades e Trabalhos em dupla para Programação Orientada a Objetos - MC322

Integrantes do grupo LEONS:
260299 | Elise Maria Rabelo Paulino Oliveira
198218 | Leonardo Paillo da Silva

Ex-integrante do grupo LEONS (descanse em paz):
~~289110 | Leon Luca de Araujo Calheira~~

## CamaLeons

CamaLeons é uma fornecedora de camas e de itens de conforto em geral.

Nossos produtos principais incluem (mas não se limitam) a:
- Camas prontas, colchões, edredons, lençóis e travesseiros;
- Sofá-camas, almofadas, sofás e poltronas;
- Camas infláveis e sacos de dormir.

As matérias-primas com as quais trabalhamos incluem:
- Madeira
- Espuma
- Algodão

A unidade atual da CamaLeons, sediada em Campinas - SP, trabalha principalmente
com produtos de madeira, visto que é o produto mais abundante na região.

# Geral

**Como funciona:**
    Você terá várias opções ao longo do seu "expediente" na fábrica, sendo a sua
interação principalmente através de menus com uma quantidade limitada, porém intuitiva,
de escolhas. Experimente bastante, tente criar produtos e aperfeiçoar as máquinas
sempre que necessário, e vise sempre o lucro mais confortável que conseguir!

    No início da simulação, você deverá escolher entre um cenário normal e outro
apocalíptico. Escolha sabiamente, baseando-se na sua própria visão de mundo: você
quer viver no conforto que pregamos, ou desafiar aos seus próprios limites?

    Quando sair da simulação, lembre-se de salvar o seu progresso, para assim poder
carregá-lo novamente da próxima vez que voltar ao trabalho.

**Recomendações:**
- Certifique-se de que você tem a versão 17 do Java instalada.  Caso contrário, você enfrentará grandes dificuldades...
- Para executar o sistema, use o comando `./gradlew run`
- Para executar um de nossos testes, use o comando `./gradlew test`
- Tente não dormir em um de nossos produtos de altíssima qualidade. Lembre-se: você está aqui para trabalhar!

**Um pequeno lembrete:**
MP = matéria-prima. Para fins de simplicidade essa é a única abreviação que será usada no código, pois é intuitiva para quem está trabalhando nele e também economiza bastante espaço.

# Escopo e Limitações
Nosso sistema conta com o mecanismo de salvar e carregar progressos anteriores por intermédio do XML. Para tal, escolhemos salvar a maior parte das informações críticas para o funcionamento e o lucro da fábrica, enquanto dados menos relevantes ou circunstanciais "nascem e morrem" dentro do contexto do expediente. Veja mais informações abaixo.

O que fica:
- O valor do orçamento. *Não tem como surgirmos com 134 milhões de reais do nada, que nem um certo filme.*
- As máquinas e suas partes. *Quando a fábrica fecha, nada é tocado até que ela seja aberta novamente.*
- Demandas. *As demandas que ainda não concluímos também ficam pendentes até que elas sejam de fato concluídas; afinal, cliente nosso leva conforto para casa, e não desaforo!!!*

O que não fica:
- Produtos no armazém. *O nosso foco aqui, como gerente, é o gerenciamento dos produtos e o atendimento das demandas. Quando fechamos a fábrica, todos os produtos vão para nossas subsidiárias locais para serem vendidas em menor escala, ou então são doadas para as nossas queridas comunidades da região.*
- Cenário de operação. *Este só diz respeito a um dia específico. Você pode muito bem ter um dos dias mais caóticos da sua vida, com muitas crises e problemas, e no dia seguinte estar tudo bem.*
- Estratégia atual. *Isso também é algo que você decide no momento que pisa na fábrica. Não faria sentido manter a mesma estratégia de outro dia, afinal você é um gerente muito qualificado e que sabe lidar com as circunstâncias do momento!*
- Matéria-prima. *Assim como os produtos, parte da nossa matéria-prima também é fornecida a produtoras locais para fomentar o comércio e movimentar a economia local. Valorizamos muito o lucro, mas também valorizamos a comunidade e o bem-estar do público ao nosso redor... é essa a nossa filosofia, o conforto sempre em primeiro lugar!*

Além disso, temos alguns casos de teste disponíveis para verificar a eficiência da nossa fábrica. Esses casos foram construídos através do JUnit 5 e podem ser acessados através de nossa sala de testes. A saber:

**MateriaPrimaTest** testa as regras de negócio e exceções do sistema. 
- *consumirMaisDoQueTemLancaExcecao* verifica se tentar consumir uma quantidade de matéria-prima maior que a que temos disponível em estoque lança a exceção correspondente.
- *adicionarEstoqueAumentaQuantidade* verifica se a lógica e a matemática de adição do sistema está correta (eba, sabemos matemática básica!!! :3).

**GerenciadorProducaoTest** testa a integração e composição de nossos componentes.
- *gerarAuditoriaGeralIncluiDiagnosticoDosBicosSemFalhar* verifica, intuitivamente, se o relatório de auditoria pedido pelo gerente disponibiliza os dados dos bicos de injeção de uma enchimax corretamente. Isso é importante pois os bicos são a parte mais "complexa" da auditoria, com os dados correspondentes sendo gerados em cascata pela função.

**PersistenciaTest** testa a infrestutura e a nossa capacidade de lembrar...
- *salvarECarregarMantemBudget* cria uma fábrica-modelo, salva os dados dela (incluindo o orçamento) e carrega de novo, verificando dessa maneira se o nosso sistema de memória está correto e funcional. Não se preocupe, aqui a gente descansa bem... e é com um bom descanso que se conquista uma boa memória!!!!!!!!!!! Bom, é isso que eu espero...

# Diário de Bordo
**TAREFA 1**
changelog 24/08
- Iniciado o projeto
- Criado o arquivo main.java

changelog 31/08
- Criados os arquivos EstacaoInspecao.java, Esteira.java,  Maquina.java, MateriaPrima.java e Produto,java.
- Adicionada uma descrição do projeto através de justificativa.txt

changelog 01/09
- Expandidos os atributos e métodos de todas as classes

changelog 02/09
- Bugfixing miscelâneo
- Aprimorados os atributos e métodos de todas as classes
- Criada uma mini interface com ações para o usuário na classe main

**TAREFA 2**
changelog 14/09
- a classe produto foi mudada para classe abstrata e foram implementadas as mudanças pedidas
- criadas também as subclasses da classe produto
- removido o arquivo justificativa.txt (só era para a tarefa 1)

**TAREFA 3**
changelog 04/10
- Deletada a Esteira.java pois não é mais usada no fluxo do programa e acabou ficando meio inútil
- Implementadas as mudanças necessárias para a Tarefa 3
- Adicionado o enumerador StatusProduto (não era necessário mas era legal)
- Descanse em paz, Leon Luca.
- readicionado o arquivo justificativa.txt

**TAREFA 4**
changelog 11/10
- Adicionada a Elise no projeto
- Implementado o Gradle

changelog 14/10
- Implementada a documentação com Javadoc (exceto para Produto+subclasses, Demanda e GerenciadorProducao)

changelog 15/10
- Implementado o tratamento de erros em algumas partes do programa

**TAREFA 5**
changelog 21/10
- Adicionados os novos componentes da EnchiMax

changelog 25/10
- Criada a linha de produção

changelog 26/10
- Ajustes finais e bugfixes. Adicionados os diagramas em uma pasta separada "uml"

**TAREFA 6**
changelog 7/10
- Refatorada a fábrica
- Adicionado o esqueleto de PersistenciaXML.java

changelog 8/10
- Adicionado o caso de teste GerenciadorProducaoTest.java
- Organizada a PersistenciaXML.java

changelog 9/10
- Finalizadas as mudanças em PersistenciaXML.java
- Adicionados outros dois casos de teste
- Escritos os Javadocs
- Reorganizado e reescrito o README.md
- Alguns bugfixes