FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY build/libs/terningserver-0.0.1-SNAPSHOT.jar /app/terningserver.jar
CMD ["java", "-Duser.timezone=Asia/Seoul", "-jar", "-Dspring.profiles.active=prod", "terningserver.jar"]
