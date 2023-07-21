FROM openjdk:11.0.11-jre-slim
RUN  apt-get update && apt-get install -y --no-install-recommends netcat-openbsd && apt-get install -y --no-install-recommends curl
RUN rm -rf /var/lib/apt/lists/*
RUN mkdir -p /usr/local/wall/registry/
ADD @project.build.finalName@.jar /usr/local/wall/registry/
ADD run.sh run.sh
RUN chmod +x run.sh
CMD ./run.sh
