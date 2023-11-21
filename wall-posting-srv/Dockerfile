FROM openjdk:11.0.11-jre-slim
RUN mv /etc/apt/sources.list /etc/apt/sources.list.bak && \
    echo "deb https://mirrors.aliyun.com/debian/ buster main non-free contrib" >/etc/apt/sources.list && \
    echo "deb https://mirrors.aliyun.com/debian-security buster/updates main" >>/etc/apt/sources.list && \
    echo "deb https://mirrors.aliyun.com/debian/ buster-updates main non-free contrib" >>/etc/apt/sources.list && \
    echo "deb https://mirrors.aliyun.com/debian/ buster-backports main non-free contrib" >>/etc/apt/sources.list

RUN  apt-get update && apt-get install -y --no-install-recommends netcat-openbsd && apt-get install -y --no-install-recommends curl
RUN rm -rf /var/lib/apt/lists/*
RUN mkdir -p /usr/local/wall/
COPY ./target/wall-posting-srv-0.0.1-SNAPSHOT.jar /usr/local/wall/app.jar
ENV PROFILE="dev"
ENV EUREKASERVER_URI="http://eurekaserver:8989/eureka/"
ENV EUREKASERVER_PORT="8989"
ENV EUREKASERVER="eurekaserver"
ENV CONFIGSERVER=configserver
ENV DATABASESERVER="host.docker.internal"
ENV KAFKABROKERS="localhost:9095"
ADD ./docker/run.sh run.sh
RUN chmod +x run.sh
CMD ./run.sh