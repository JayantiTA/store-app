FROM maven:3-openjdk-17

WORKDIR /online-store-app
COPY . .
RUN mvn clean install -DskipTests

CMD mvn spring-boot:run