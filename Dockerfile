FROM maven:3-eclipse-temurin-21

WORKDIR /app

COPY pom.xml .
COPY .mvn .mvn
COPY src src

RUN mvn package -Dmaven.test.skip=true

ENV PORT=3000
ENV SPRING_REDIS_HOST=null
ENV SPRING_REDIS_PORT=null
ENV SPRING_REDIS_USERNAME=null
ENV SPRING_REDIS_PASSWORD=null

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar target/addressbook-0.0.1-SNAPSHOT.jar 
