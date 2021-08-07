# ordem-servico
Projeto que demonstra crud de ordem de serviço, utilizando clientes, equipamentos, enums e alguns padrões de projeto.<br>
Foram utilizados alguns frameworks para facilitar o desenvolvimento, como: Spring, Hibernate, Lombok, Swagger e REST, além de execução de teste unitário utilização de alguns patterns entre as classes.  
* É possível cadastrar todos os registros necessários para a geração de uma ordem de serviço, como Marcas, Equipamentos, Clientes.
* Esse projeto foi desenvolvido com java 14.
* É possível executá-lo tanto com banco de dados MySQL quanto com H2<br>
    Para utilizar com H2, descomente o bloco ### H2 ### no arquivo application.properties e compile o projeto... abra o navegador e digite "localhost:8080/h2-console" (sem aspas) para ser direconado para a tela de login... basta clicar em "Connect" que terá acesso às tabelas criadas. Vale lembrar que é um banco em memória e o mesmo é recriado a cada build.
    Para utilizar o MySQL, descoment o bloco ### MYSQL ### no arquivo application.properties e configure a sua senha de conexão ao banco de dados... compile e após o OK de compilação, acesse seu gerenciador de banco de dados para visualizar as tabelas... no caso do MySQL não está recriando a estrutura de banco - melhor p/ fazer testes onde não quer ficar incluindo novamente os dados.
* Por padrão, o Tomcat vai subir a aplicação na porta 8080... se essa porta já estiver ocupada e quiser alterar, basta alterar no arquivo application.properties, a propriedade "server.port=".
* É possível acessar todos os métodos da Controller pelo PostMan. Caso queira acessar pelo mesmo, acesse todas as controllers para ver os contratos.
* Para melhor acesso, toda a API já está documentada para acessar via Swagger... basta acessar "localhost:8080/swagger-ui.html" que terá acesso a todos os contratos. Pelo Swagger é possível visualizar exemplos de estrutura que deverá ser enviada e também da forma que será o retorno.
    > se for executar em outra máquina, altere o localhost do H2 e do Swagger para o ip correto.
* Existem testes unitários que foram feitos a título de exemplo, um para camada service de clientes, outra para camada service de equipamentos e outro para camada de ordem de serviço... São testes unitários e apenas exemplifica a chamada e comparação.
* A "OrdemServicoService" é a única classe que está documentada (ao meu ver é a que contém mais processos mais ainda assim ficou bem simples sua utilização).
* Em todas as Controllers, tem métodos para fazer o CRUD.
* A Controller OrdemServicoController, possui ainda uma consulta paginada onde é possível incluir filtros para facilitar a busca, como tipo do estágio, cliente, equipamento etc.



