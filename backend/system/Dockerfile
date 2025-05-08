FROM adoptopenjdk/openjdk11:jre11u-nightly
COPY target/system.jar system.jar
ENTRYPOINT ["java","-jar","/system.jar"]