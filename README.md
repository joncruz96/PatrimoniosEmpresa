# Patrimonios Empresa

Sistema para armazenar patrimonios de uma empresa através de API's REST


## API's REST

- GET()
- GETALL()
- POST()
- PUT()
- DELETE()

## Classes

- Patrimonio
- Marca
- Usuario

## Como utilizar

Realize o clone do projeto para sua máquina e baixe as dependências necessárias do Maven.

**Postgres**

Crie um banco de dados com o nome patrimonios-empresa. 

O usuário e senha do Database do Postgres são os padrões. User: postgres Password: postgres@123.

**Obs. 01:** Caso não seja este usuário e senha é só alterar no arquivo application.properties o seu usuário e senha do Postgres. 

**Obs. 02:** Não foi necessário um arquivo com os códigos SQL no projeto, pois a criação das tabelas e atributos é feita automaticamente quando sobe a aplicação, assim é criado de acordo com a nomenclatura das entidades e atributos das classes. 



## Start Aplicação

Após subir a aplicação através do Spring Boot, é possível visualizar e realizar os testes das API's REST através do **Swagger** acessando por:
 http://localhost:8080/swagger-ui.html#/ 

Por ele é possível verificar quais as apis disponíveis, o que é obrigatório informar e os modelos de cada DTO.

**Autenticação com Token**

Para acessar os endpoints é necessário autenticação do usuário já cadastrado no sistema. 

Obs.: Os endpoints que não são necessários autenticação é o POST da criação de usuário e o POST de auth, que é onde irá adquirir o token do usuário. 

Após cadastrar o usuário execute o endpoint POST da autenticação-controller para obter o Token.

**Acesso aos Endpoints**

Digite "Bearer" e informe o Token no campo "Header para token JWT" em todo endpoint que for utilizar.

Exemplo: Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBUEkgZG9zIFBhdHJpbW9uaW9zIEVtcHJlc2EiLCJzdWIiOiIxIiwiaWF0IjoxNTk0MTcxNjg4LCJleHAiOjE1OTQyNTgwODh9.xNlNvrQ37jutOOtRGAFaR4AEVHwTKqPLdBOt49ehQ7c
