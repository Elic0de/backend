FROM maven:3-eclipse-temurin-17 as build
WORKDIR /myapp
COPY pom.xml pom.xml
COPY src src
COPY conf conf
RUN mvn package

FROM eclipse-temurin:17-jdk
WORKDIR /myapp
COPY --from=build /myapp3/target/myapp-1.0.0.jar app.jar
COPY conf conf
EXPOSE 8082
CMD ["java", "-jar", "app.jar"]
