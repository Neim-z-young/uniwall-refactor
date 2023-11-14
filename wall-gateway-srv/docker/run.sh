#!/bin/sh

echo "********************************************************"
echo "Waiting for the eureka server to start  on port $EUREKASERVER_PORT"
echo "********************************************************"
while ! `nc -z $EUREKASERVER $EUREKASERVER_PORT`; do sleep 3; done
echo ">>>>>>>>>>>> Eureka Server has started"

echo "********************************************************"
echo "Waiting for the config server to start  on port 7777"
echo "********************************************************"
while ! `nc -z $CONFIGSERVER 7777`; do sleep 3; done
echo ">>>>>>>>>>>> Config Server has started"

echo "********************************************************"
echo "Starting Configuration Service with Eureka Endpoint:  $EUREKASERVER_URI";
echo "********************************************************"
java -Djava.security.egd=file:/dev/./urandom \
     -Dspring.profiles.active=$PROFILE \
     -Deureka.client.serviceUrl.defaultZone=$EUREKASERVER_URI \
     -Dconfigserver-name=$CONFIGSERVER \
     -Dreactor.netty.http.server.accessLogEnabled=true \
     -jar /usr/local/wall/app.jar
