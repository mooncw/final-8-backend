FROM openjdk:21-jdk

COPY build/libs/BE-final-0.0.1-SNAPSHOT.jar neuroflow/neuroflow-app.jar

WORKDIR /neuroflow
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "neuroflow-app.jar"]
