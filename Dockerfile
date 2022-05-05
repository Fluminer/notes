FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=build/libs/notes-1.0-release.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]