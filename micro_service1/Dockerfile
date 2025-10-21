FROM openjdk:17-jdk-slim

WORKDIR /app

# Copier le JAR généré
COPY target/micro_service1-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port (ajuste selon ta configuration)
EXPOSE 8080

# Commande de démarrage
ENTRYPOINT ["java", "-jar", "app.jar"]