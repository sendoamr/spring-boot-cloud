## Modules
- spring-boot-open: principal microservice to get packages data
- spring-boot-admin: tool to managment the microservices
- spring-boot-eureka: tool to managment the microservices
- sonar-analytics: docker images to launch docker and send info of jacoco of microservices
- registry to pull and push docker images
- Postman: Collection for call to service


## build microservice and admin
```
mvn clean install
```
Launch first spring boot admin running in <http://localhost:9001> admin/admin and eureka running in <http://localhost:9002>
```
java -jar spring-boot-admin/target/spring-boot-admin-1.0.0.jar
java -jar spring-boot-admin/target/spring-boot-eureka-1.0.0.jar
```
Launch microservice and it should register data in admin at the moment
```
java -jar spring-boot-open/target/spring-boot-open-1.0.0.jar
```
At now, the service are available in <http://localhost:9080>

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

## Next steps
 - Create profiling for diferent environments (local, local-cloud, ...)
 - Create a new experimental Reactive sandbox service with persistence
 - Integrate zuul and config server
 - Processing logs with elk
 - Create a demo webapp with reactive client

