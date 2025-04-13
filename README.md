# MiauSocial 🐱

Bem-vindo ao **MiauSocial**! Este é um projeto desenvolvido em **Spring Boot** utilizando **Maven** e **PostgreSQL**.

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- **Maven**
- **PostgreSQL**
- **Docker (opcional)**

## 📂 Como Clonar o Repositório

```sh
# Via HTTPS
git clone https://github.com/seu-usuario/miausocial.git

# Via SSH
git clone git@github.com:seu-usuario/miausocial.git
```

Entre no diretório do projeto:
```sh
cd miausocial
```

## ⚙️ Configuração do Banco de Dados

Antes de rodar o projeto, configure o banco de dados **PostgreSQL**. Se estiver usando Docker, pode rodar:

```sh
docker run --name miausocial-db -e POSTGRES_DB=miausocial -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin -p 5432:5432 -d postgres
```

Caso prefira configurar manualmente, ajuste o `application.yml` com as credenciais corretas.

## ▶️ Como Rodar o Projeto

1. Certifique-se de ter o **JDK 17+** instalado.
2. Compile o projeto com Maven:
   ```sh
   mvn clean install
   ```
3. Rode o projeto:
   ```sh
   mvn spring-boot:run
   ```

O projeto estará disponível em: [http://localhost:8080](http://localhost:8080)

## 🛠 Construindo um JAR Executável
Se quiser gerar um JAR executável, rode:
```sh
mvn clean package
java -jar target/miausocial-0.0.1-SNAPSHOT.jar
```

## 📄 Licença
Este projeto está sob a licença MIT. Sinta-se à vontade para contribuir! 😺

