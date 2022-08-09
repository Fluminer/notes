FROM gradle:7.5-jdk18
ARG DEBIAN_FRONTEND=noninteractive
ENV TZ=Europe/Moscow
WORKDIR /app
COPY . .
RUN gradle bootJar
FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /app
COPY --from=0 /app/build/libs/notes-1.0-release.jar ./notes.jar
#COPY build/libs/notes-1.0-release.jar /notes.jar
ENTRYPOINT [ "java", "-jar","/app/notes.jar" ]