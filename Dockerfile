FROM gradle AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon -x test

FROM eclipse-temurin:17-jdk-focal
COPY --from=build /home/gradle/src/build/libs/blazebankapigateway-0.0.1-SNAPSHOT.jar /app/blazebankapigateway-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "app/blazebankapigateway-0.0.1-SNAPSHOT.jar"]