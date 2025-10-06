# Microservice Initial

Este projeto é uma base para construção de microsserviços utilizando Java e Spring Boot. Ele está dividido em dois serviços principais:

- **Product Service**: Gerencia produtos e estoque.
- **Order Service**: Gerencia pedidos e realiza comunicação com o serviço de produtos via RabbitMQ.

## 🧱 Estrutura do Projeto

```
microservice-initial/
├── product/       # Microsserviço de produtos
└── orders/        # Microsserviço de pedidos
```

Cada microsserviço é independente e possui sua própria estrutura de projeto, incluindo:

- Spring Boot
- Maven
- RabbitMQ para comunicação assíncrona
- Banco de dados

## 🚀 Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- RabbitMQ
- Maven
- Lombok
- JUnit

## 🔄 Comunicação entre Microsserviços

A comunicação entre os microsserviços é feita via **RabbitMQ**, permitindo que o serviço de pedidos envie mensagens ao serviço de produtos para atualizar o estoque após a criação de um pedido.

## 📦 Como Executar

### Pré-requisitos

- Java 17+
- Maven
- RabbitMQ

### Passos

1. Clone o repositório:
   ```bash
   git clone https://github.com/vit0ur/microservice-initial.git
   ```

2. Inicie o RabbitMQ (via Docker):
   ```bash
   docker run -d --hostname rabbitmq --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
   ```

3. Navegue até cada microsserviço e execute:
   ```bash
   cd product
   mvn spring-boot:run
   ```

   ```bash
   cd orders
   mvn spring-boot:run
   ```

## 📬 Endpoints

Cada serviço possui seus próprios endpoints REST. Exemplos:

### Product Service

- `GET /products`
- `POST /products`

### Order Service

- `GET /orders`
- `POST /orders` (envia mensagem para atualizar estoque)
