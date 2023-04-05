FROM openjdk:17.0-slim
WORKDIR /app

COPY build/libs/gateway-server-0.0.1-SNAPSHOT.jar .

EXPOSE 8071
ENTRYPOINT [ "java", "-jar", "gateway-server-0.0.1-SNAPSHOT.jar" ] 