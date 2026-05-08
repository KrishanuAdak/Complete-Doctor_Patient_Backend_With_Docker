FROM eclipse-temurin:17
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 9000
ENTRYPOINT [ "java", "-jar", "app.jar" ]