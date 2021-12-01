<h1 align="center">
  Api e-commercer
</h1>


<p align="center">
  <a href="#-tecnologias">Tecnologias</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#-projeto">Projeto</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#-url">Url</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
</p>


<br>


## 🚀 Tecnologias

Esse projeto foi desenvolvido com as seguintes tecnologias:

- [Spring](https://spring.io/)
- [Jpa](https://spring.io/projects/spring-data-redis)
- [Redis](redis)
- [gateway](https://spring.io/projects/spring-cloud-gateway)
- [netflix-eureka](https://spring.io/projects/spring-cloud-sleuth)
- [Postgresql](https://www.postgresql.org/)
- [lombok](https://projectlombok.org/)
- [swagger](https://swagger.io/)


## 💻 Projeto

Desenvolvimento serviços web RESTful . A combinação de Spring Boot, Spring Web MVC, Spring Web Services,
JPA e Microsserviços. Serviços web RESTful desenvolvendo tratamento de exceção, 
documentação (Swagger) e implementando microsserviços usando o Spring Cloud. 
Estabelecendo comunicação entre microsserviços, possibilitar o balanceamento de carga, 
dimensionando para cima e para baixo de microsserviços, 
implementando o Eureka Naming Server e usando banco de dados em memória distribuído redis. 
Esse projeto tem como objetivo desmotar a cuminicação entre microsserviço.




## :hammer: Para executar o projeto no terminal, digite o seguinte comando:

```shell script
mvn spring-boot:run 
```

## 💻 Url
Após executar o comando acima, basta apenas abrir o seguinte endereço e visualizar a execução do projeto:

```
Product
http://localhost:8000/product
http://localhost:8000/product/all
http://localhost:8000/product/{id}

User
http://localhost:8811/user
http://localhost:8811/user/all
http://localhost:8811/user/{id}

Order
http://localhost:8100/cart/{cartid}
http://localhost:8100/cart/createItem/{id}
http://localhost:8100/cart/createItem/{cartid}/{id}
http://localhost:8100/cart/createItem/{id}
http://localhost:8100/order/user/{id}
http://localhost:8100/order/{id}

```



## 📝 Licença

Este projeto esta sobe a licença MIT. Veja a [LICENÇA](https://opensource.org/licenses/MIT) para saber mais.


