#Start local registry
docker run -d -p 5000:5000 --restart=always --name registry registry:2

# Build tag and push Java image to registry 
docker build -t "opendata_java" .
docker tag opendata_java localhost:5000/java
docker push localhost:5000/java