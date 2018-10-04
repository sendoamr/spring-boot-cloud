#Start dockers of sonaq in 
docker-compose -f docker-compose-sonar.yml up

#go to microservice and send sonar report
cd ../spring-open-data
mvn clean test
mvn sonar:sonar -Dsonar.host.url=http://localhost:9000

#Stop sonars services
docker-compose -f docker-compose-sonar.yml down