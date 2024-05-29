# Use an alpine linux image
FROM alpine:3.19 AS build

# Install necessary packages for Java and Maven
RUN apk update
RUN apk add openjdk21 gcompat --repository=https://dl-cdn.alpinelinux.org/alpine/v3.19/community
RUN apk add maven gcompat --repository=https://dl-cdn.alpinelinux.org/alpine/v3.19/community

# Set JAVA_HOME environment variable
ENV JAVA_HOME=/usr/lib/jvm/java-21-openjdk

# Copy the project files into the container
COPY . .

# Build the project
RUN mvn clean package -DskipTests -Dmaven.connection.timeout=120000

# Use a JDK runtime image
FROM eclipse-temurin:21-jdk-alpine

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Copy the built file into the container
COPY --from=build /target/ramen-go-0.0.1-SNAPSHOT.jar app.jar

# Copy the .env file into the container
COPY .env .env

# Run the application
CMD ["java", "-jar", "app.jar"]
