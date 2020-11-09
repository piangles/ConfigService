FROM java:8
WORKDIR /
ADD ConfigService.jar ConfigService.jar
ENTRYPOINT ["java", "-Dprocess.name=ConfigService", "-jar", "ConfigService.jar"]
