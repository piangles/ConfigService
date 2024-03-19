FROM eclipse-temurin:17-jre-alpine
WORKDIR /
ADD ./target/ConfigService.jar ConfigService.jar
ENTRYPOINT ["java", "-Dprocess.name=ConfigService", "-jar", "ConfigService.jar"]
