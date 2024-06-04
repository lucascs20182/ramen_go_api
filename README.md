# ramenGO! API

Live on [https://ramen-go-api-prod.onrender.com/v1](https://ramen-go-api-prod.onrender.com/v1)

## Here's how to use this API in production

This API is hosted on the Render platform, and since it's a free instance, it shuts down due to inactivity - which can delay requests by 50 seconds or more. That's why I recommend to acess the [API documentation page](https://ramen-go-api-prod.onrender.com/v1) in the first time using it, then once the Swagger page is visible it'll be ready for use and validate with tools like [https://tech.redventures.com.br](https://tech.redventures.com.br).

## How to deploy to production

The project was set up for auto-deploy on the Render platform, so every push or merge to the main branch triggers a deployment based on the [Dockerfile](Dockerfile) instructions.

## How to set up the local environment

0. Set up the environment configs creating a .env file based on the [.env.example](.env.example)

    You can simply copy the [.env.example](.env.example) file and rename it to .env, without making any changes to run it in the local development environment. Default credentials are already defined.

1. [Install Docker](https://docs.docker.com/desktop/install/ubuntu/)

2. (Option 1) Run the entire packaged application

    - Start the project's containers with `docker compose -f docker-compose-build.yaml up -d --build`

    - Access the API from http://localhost:8080/

    - Access pgadmin from http://localhost:16543/

    - Stop the containers with `docker compose -f docker-compose-build.yaml down`

2. (Option 2) Run the application in development mode

    - Install Java and Maven

        - Java version
            ```bash
            openjdk 21.0.2 2024-01-16
            OpenJDK Runtime Environment (build 21.0.2+13-Ubuntu-120.04.1)
            OpenJDK 64-Bit Server VM (build 21.0.2+13-Ubuntu-120.04.1, mixed mode, sharing)
            ```

        - Maven version:
            ```bash
            Apache Maven 3.6.3
            Maven home: /usr/share/maven
            Java version: 21.0.2, vendor: Private Build, runtime: /usr/lib/jvm/java-21-openjdk-amd64
            Default locale: en_US, platform encoding: UTF-8
            OS name: "linux", version: "5.18.0-051800-generic", arch: "amd64", family: "unix"
            ```

    - Start the postgresql and pgadmin containers with `docker compose -f docker-compose-dev.yaml up -d`

    - Start the API without debug running `./mvnw spring-boot:run`

    - Debug the server by installing [Spring Boot Extension Pack](https://marketplace.visualstudio.com/items?itemName=vmware.vscode-boot-dev-pack) and pressing F5 (live reload enabled)

    - Stop containers with `docker compose -f docker-compose-dev.yaml down`

## How to run tests

- Use the [application-test.properties](src/main/resources/application-test.properties) profile

- Run tests with `./mvnw test`

- Check test coverage with `./mvnw clean test jacoco:report`

    80%+ unit test coverage.
