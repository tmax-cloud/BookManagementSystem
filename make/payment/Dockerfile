FROM openjdk:11

#HEALTHCHECK CMD curl --fail -s http://localhost:8080/api/ping || curl -k --fail -s https://localhost:8443/api/ping || exit 1

ADD ./make/payment/payment.jar /app/

WORKDIR /app

CMD ["java", "-jar", "payment.jar"]