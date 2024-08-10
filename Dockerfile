# Use a base image with OpenJDK and Maven
FROM maven:3.8.5-openjdk-17-slim AS builder

WORKDIR /app

# Copy the Maven project file
COPY pom.xml .

# Copy the project source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use a lightweight image to run the application
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

# Command to run the application
CMD ["java", "-jar", "app.jar"]
