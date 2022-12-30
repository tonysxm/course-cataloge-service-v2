# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.1/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.0.1/gradle-plugin/reference/html/#build-image)
* [Testcontainers MariaDB Module Reference Guide](https://www.testcontainers.org/modules/databases/mariadb/)
* [JDBC API](https://docs.spring.io/spring-boot/docs/3.0.1/reference/htmlsingle/#data.sql)
* [Testcontainers](https://www.testcontainers.org/)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.0.1/reference/htmlsingle/#web)

### Guides

The following guides illustrate how to use some features concretely:

* [Accessing Relational Data using JDBC with Spring](https://spring.io/guides/gs/relational-data-access/)
* [Managing Transactions](https://spring.io/guides/gs/managing-transactions/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Additional Links

These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)


### HOW-TO Install and run

* ./gradlew clean build [root folder] - Generates application from the open api specs
* ./gradlew bootRun

### Create and Run Docker Image

* ./gradlew bootBuildImage
* docker-compose up -d
