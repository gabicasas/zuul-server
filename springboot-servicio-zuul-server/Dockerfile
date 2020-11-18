FROM openjdk:11
EXPOSE 8090
VOLUME /tmp

ADD ./target/springboot-servicio-zuul-server-0.0.1-SNAPSHOT.jar zuul-server.jar
ENTRYPOINT ["java", "-jar", "zuul-server.jar"]
#crear docker build config-server:v1 .
#

#docker run -p 8888<el que sea>:8888 --name <asdfafconfig-server> --network network-spring config-server:v1


