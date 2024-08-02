1 - Essa aplicação tem como objetivo cadastrar, alterar e consultar contas!

Pré requisitos

1 - Docker instalado.
2 - Postman ou terminal ou simulares para execução dos curls.

Rodar a aplicação

- Na raiz do projeto existem dois arquivos Docker e docker-compose.yml

- Via terminal execute o comando docker compose up -d

- Verifique se o actuator está retornando UP

curl --location 'http://localhost:8080/actuator/health'

Funcionalidades

Obs: Credenciais para o Basic e Banco de dados.
  username: conta
  password: conta123

1 - Cadastrar conta

curl --location 'http://localhost:8080/contas' \
--header 'Authorization: Basic Y29udGE6Y29udGExMjM=' \
--header 'Content-Type: application/json' \
--data '{
    "dataVencimento": "2024-07-31",
    "dataPagamento": "2024-07-31",
    "valor": 50,
    "descricao": "sdadasdasds"
}'

2 - Consulta de conta por Id.

curl --location 'http://localhost:8080/contas/1' \
--header 'Authorization: Basic Y29udGE6Y29udGExMjM='

3 - Consulta de contas com filtros e paginação

curl --location 'http://localhost:8080/contas?dataVencimento=2024-07-30&page=0&size=10' \
--header 'Authorization: Basic Y29udGE6Y29udGExMjM='

curl --location 'http://localhost:8080/contas?desricao=TESTE&page=0&size=10' \
--header 'Authorization: Basic Y29udGE6Y29udGExMjM='

4 - Consulta valor total por período.

curl --location 'http://localhost:8080/contas/total?dataInicio=2024-07-28&dataFim=2024-07-29' \
--header 'Authorization: Basic Y29udGE6Y29udGExMjM='

5 - Atualizar situação de conta.

curl --location --request PATCH 'http://localhost:8080/contas/1' \
--header 'Authorization: Basic Y29udGE6Y29udGExMjM=' \
--header 'Content-Type: application/json' \
--data '{
    "situacao": "PAGO"
}'

6 - Atualizar conta.

curl --location --request PUT 'http://localhost:8080/contas/1' \
--header 'Authorization: Basic Y29udGE6Y29udGExMjM=' \
--header 'Content-Type: application/json' \
--data '{
    "dataVencimento": "2024-07-31",
    "dataPagamento": "2024-07-31",
    "valor": 50,
    "descricao": "sdadasdasds"
}'

7 - Upload e processamento de csv para cadastro de contas em massa.

Obs: O formato do csv precisa seguir o exemplo a baixo.

Data Vencimento;Data Pagamento;Valor;Descricao
2025-04-17;2025-04-17;30;teste

curl --location 'http://localhost:8080/contas/upload' \
--header 'Authorization: Basic Y29udGE6Y29udGExMjM=' \
--form 'file=@"/C:/Users/Gustavo Felix/Desktop/teste.csv"'


