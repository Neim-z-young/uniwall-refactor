FROM openjdk:11.0.11-jre-slim
RUN  apt-get update && apt-get install -y --no-install-recommends netcat-openbsd && apt-get install -y --no-install-recommends curl
RUN rm -rf /var/lib/apt/lists/*
RUN mkdir -p /usr/local/wall/
COPY ./target/wall-registry-srv-1.0-SNAPSHOT.jar /usr/local/wall/app.jar
ENV EUREKASERVER_PORT=8989
CMD java \
    -Djava.security.egd=file:/dev/./urandom \
    -Dserver.port=$EUREKASERVER_PORT \
    -jar /usr/local/wall/app.jar
