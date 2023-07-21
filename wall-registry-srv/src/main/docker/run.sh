#!/bin/sh

echo "********************************************************"
echo "Starting Registry Service with Eureka Port:  $EUREKASERVER_PORT";
echo "********************************************************"
java -Djava.security.egd=file:/dev/./urandom \
    -Dserver.port=$EUREKASERVER_PORT \
    -jar /usr/local/wall/registry/@project.build.finalName@.jar
