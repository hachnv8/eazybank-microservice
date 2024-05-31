# Start with a base image containing Java runtime
FROM openjdk:21-jdk-slim

# Information about who maintains the image
MAINTAINER hacheery.com

# Add the application's jar to the image
COPY target/account-service-0.0.1-SNAPSHOT.jar account-service-0.0.1-SNAPSHOT.jar

# Execute the application
ENTRYPOINT ["java", "-jar", "account-service-0.0.1-SNAPSHOT.jar"]