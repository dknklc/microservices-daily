# Any time whenever I want to run my account microservice, the very first basic requirement that I have is to install
# JRE or Java inside any system.
# Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim

# Who is the maintainer of the Docker image that we are going to create
MAINTAINER dekankilic

# What I am saying is that there is jar with the name accounts-0.0.1-SNAPSHOT.jar under target folder,
# Take this jar file, and copy the same jar into my Docker image.
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

# Whenever someone tries to generate a Docker container from this image, please execute the following command.
ENTRYPOINT ["java", "-jar", "accounts-0.0.1-SNAPSHOT.jar"]