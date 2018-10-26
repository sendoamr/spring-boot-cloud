## Build and run the microservice with maven

```bash
mvn clean install
java -jar target/spring-boot-open-1.0.0.jar
```
The server will start at <http://localhost:9080>.

## Register and build docker image of microservice
Read registry folder steps to launch a registry local.
```
mvn clean install docker:build
docker run localhost:5000/spring-boot-open
```

## Run dynamic Junit5 tests
```
mvn test
```

## Rest APIs doc

The application defines following REST APIs

```
GET /api/v1/packages - Get All data
Posible query params
	- pageKey: num of page selected
	- pageSize: max 30 register for page
	- sort: sort field
	- direction: asc or dec

GET /v1/packages/{id} - Get One package data

GET /v1/packages/all - Get all packages reactive

```

## Swagger 2 json definition
    /v2/api-docs