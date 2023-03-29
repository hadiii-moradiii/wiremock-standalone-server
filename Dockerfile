FROM openjdk:11.0.8-jdk
USER root
ARG DEBIAN_FRONTEND=noninteractive

# RUN apt-get update \
#    && apt-get -yq  install apt-utils \
#    && apt-get -yq  install curl \
#    && apt-get -yq  install telnet \
#    && apt-get -yq  install net-tools \
#    && apt-get install -y ca-certificates-java

RUN echo "Env variable :  $JAVA_OPTS"

ENV TZ=Asia/Tehran
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

EXPOSE 3456 3456
ARG JAR_FILE=target/mock-server-jar-with-dependencies.jar
COPY ${JAR_FILE} mock-server.jar
RUN mkdir -p /opt/app/mappings
COPY src/main/resources/mappings/ /opt/app/mappings/
############ for develop and test ############
ENTRYPOINT ["java", "-jar", "mock-server.jar"]
