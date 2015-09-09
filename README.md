## springboot-data-rest
Simple Spring Boot project that uses JPA and Rest.
Provides Docker support.

### To run this program on Docker run these commands
mvn package docker:build
docker run -p 8080:8080 -t polinchw/springboot-data-rest
