FROM openjdk:17-jdk-slim
WORKDIR /app
COPY build/libs/todo-0.0.1-SNAPSHOT.jar todo-app.jar
ENTRYPOINT ["java", "-jar", "todo-app.jar"]