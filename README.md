
# Run it
./mvnw spring-boot:run

# Actuator and health
http://localhost:8080/actuator

# Guides
https://spring.io/guides/gs/spring-boot/

# Docker
docker build -f Dockerfile -t gains-tracker .
docker run -p 8089:8088 gains-tracker