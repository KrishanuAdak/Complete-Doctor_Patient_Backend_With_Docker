FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 5000
EXPOSE 8761
ENTRYPOINT ["java","-jar","app.jar"]
