FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/task-management-0.0.1-SNAPSHOT.jar /app/task-management-system.jar
CMD ["java", "-jar", "task-management-system.jar"]
#EXPOSE 8080
