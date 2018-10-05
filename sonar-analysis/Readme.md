## Start dockers of sonaq in 
```bash
docker-compose -f docker-compose-sonar.yml up
```

## go to microservice and send sonar report
```bash
cd ../spring-open-data
mvn clean test
mvn sonar:sonar -Dsonar.host.url=http://localhost:9000
```

## Stop sonars services
```bash
docker-compose -f docker-compose-sonar.yml down
```