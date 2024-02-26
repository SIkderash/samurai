FROM adoptopenjdk/openjdk17:alpine-jre
ADD out/artifacts/samurai_jar/samurai.jar samurai.jar
ENTRYPOINT ["java", "-jar", "samurai.jar"]
EXPOSE 8080
