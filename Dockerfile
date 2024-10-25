##
## Build Stage
##
FROM gradle:8.10.2-jdk17 AS build

COPY --chown=gradle:gradle . /home/gradle/git-inspector

WORKDIR /home/gradle/git-inspector

RUN gradle build --no-daemon

##
## Run Stage
##
FROM openjdk:17-jdk-slim

COPY --from=build /home/gradle/git-inspector/build/libs/*.jar app.jar

ENTRYPOINT java -XX:-OmitStackTraceInFastThrow -jar /app.jar
