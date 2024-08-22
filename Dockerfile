FROM openjdk:17-jdk-slim
WORKDIR /app
COPY build/libs/task-management-system.jar /app/task-management-system.jar
CMD ["java", "-jar", "task-management-system.jar"]
EXPOSE 8080
