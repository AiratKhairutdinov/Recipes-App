FROM openjdk:11

WORKDIR /app

COPY src/docker/recipes-0.0.1-SNAPSHOT.jar  ./

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "recipes-0.0.1-SNAPSHOT.jar"]

