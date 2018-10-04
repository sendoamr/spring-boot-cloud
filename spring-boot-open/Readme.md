#Build and run the app with maven

```bash
mvn clean install
java -jar target/webflux-demo-0.0.1-SNAPSHOT.jar
```

#profiles
default: launch only the service
cloud: should launch fisrt spring-boot-admin

The server will start at <http://localhost:9080>.

#Run in docker the app with maven
Read first registry steps
mvn clean install docker:build
docker run localhost:5000/sendoa-boot

## Rest APIs doc

The application defines following REST APIs

```
GET /v1/data - Get All data
Posible query params
	- pageKey: num of page selected
	- pageSize: max 30 register for page
	- sort: sort field
	- direction: asc or dec

GET /v1/data/{id} - Get All data

```