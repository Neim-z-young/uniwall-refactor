FROM openjdk:11.0.11-jre-slim
RUN  apt-get update && apt-get install -y --no-install-recommends netcat-openbsd && apt-get install -y --no-install-recommends curl
RUN rm -rf /var/lib/apt/lists/*
RUN mkdir -p /usr/local/wall/
COPY ./target/wall-user-srv-0.0.1-SNAPSHOT.jar /usr/local/wall/app.jar
ENV PROFILE="dev"
ENV EUREKASERVER_URI="http://eurekaserver:8989/eureka/"
ENV EUREKASERVER_PORT="8989"
ENV EUREKASERVER="eurekaserver"
ENV CONFIGSERVER=configserver
ENV DATABASESERVER="host.docker.internal"
ADD ./docker/run.sh run.sh
RUN chmod +x run.sh
CMD ./run.sh