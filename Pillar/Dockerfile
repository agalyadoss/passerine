FROM openjdk:11

# add our user and group first to make sure their IDs get assigned consistently, regardless of whatever dependencies get added
RUN groupadd -r ssoadming && useradd -r -g ssoadming ssoadmin

ADD . /app/
WORKDIR /app
RUN mkdir /config/
RUN mkdir -p /etc/heaerie/pillar/conf/
RUN cp /app/config/*  /etc/heaerie/pillar/conf/

RUN apt-get update -y
RUN apt-get upgrade -y
RUN apt-get install  -y maven
RUN mvn install -DskipTests
ENTRYPOINT ["java"]

EXPOSE 443
CMD ["-jar", "/app/pillar-sync/target/PillarSync-1.0-jar-with-dependencies.jar"]

