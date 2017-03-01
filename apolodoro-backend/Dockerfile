FROM library/openjdk:latest

EXPOSE 8080

RUN mkdir /etc/apolodoro
COPY ./target/apolodoro-backend-0.1.0.jar /etc/apolodoro


CMD [ "java", "-jar", "/etc/apolodoro/apolodoro-backend-0.1.0.jar" ]


