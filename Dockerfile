FROM maven:3.8.5-openjdk-17 as builder

ADD ./pom.xml /tmp

ADD src/ /tmp/src/

WORKDIR /tmp

RUN ls

RUN mvn package -Dmaven.test.skip=true 

FROM maven:3.8.5-openjdk-17

RUN ls

COPY --from=builder /tmp/target/cbyk-test-0.0.1-SNAPSHOT.jar /tmp/


CMD ["java", "-jar", "/tmp/cbyk-test-0.0.1-SNAPSHOT.jar"]
