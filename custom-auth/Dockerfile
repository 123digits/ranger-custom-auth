FROM common/ubi8-maven-java17:1.0.0 AS build_jar

# Build JAR for custom authentication plugin
#mvn clean install

FROM apache/ranger:2.5.0

COPY --from=build_jar target/my.jar /opt/ranger/lib/

# RUN COMMAND