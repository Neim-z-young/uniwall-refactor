FROM openjdk:11.0.11-jre-slim
RUN mv /etc/apt/sources.list /etc/apt/sources.list.bak && \
    echo "deb https://mirrors.aliyun.com/debian/ buster main non-free contrib" >/etc/apt/sources.list && \
    echo "deb https://mirrors.aliyun.com/debian-security buster/updates main" >>/etc/apt/sources.list && \
    echo "deb https://mirrors.aliyun.com/debian/ buster-updates main non-free contrib" >>/etc/apt/sources.list && \
    echo "deb https://mirrors.aliyun.com/debian/ buster-backports main non-free contrib" >>/etc/apt/sources.list

RUN  apt-get update && apt-get install -y --no-install-recommends netcat-openbsd && apt-get install -y --no-install-recommends curl
RUN rm -rf /var/lib/apt/lists/*
RUN mkdir -p /usr/local/wall/
COPY ./target/wall-registry-srv-1.0-SNAPSHOT.jar /usr/local/wall/app.jar
ENV EUREKASERVER_PORT=8989
CMD java \
    -Djava.security.egd=file:/dev/./urandom \
    -Dserver.port=$EUREKASERVER_PORT \
    -jar /usr/local/wall/app.jar
