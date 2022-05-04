FROM adoptopenjdk/openjdk11:alpine-jre
VOLUME /tmp
COPY build/libs/notes-1.0-release.jar /notes.jar
ENTRYPOINT [ "java", "-jar","/notes.jar" ]