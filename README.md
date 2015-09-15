## springboot-data-rest
Simple Spring Boot project that uses JPA and Rest.
Provides Docker support.

### To run this program on Docker run these commands

#### Maven command to build the Docker image
mvn package docker:build
#### Run the Docker image on Docker
docker run -p 8080:8080 -t polinchw/springboot-data-rest
#### Run the Docker image on Docker and overriding a config parameter 
docker run -e "host=docker" -p 8080:8080 -t polinchw/springboot-data-rest
