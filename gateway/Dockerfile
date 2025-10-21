# Étape 1 : Build Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copier le pom.xml d'abord pour exploiter le cache Docker
COPY pom.xml .

# Télécharger les dépendances (cette étape sera mise en cache si le pom.xml ne change pas)
RUN mvn dependency:go-offline -B

# Copier le code source
COPY src ./src

# Compiler le projet
RUN mvn clean package -DskipTests

# Étape 2 : Runtime
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copier le jar compilé
COPY --from=build /app/target/*.jar app.jar

# Exposer le port du Gateway
EXPOSE 8082

# Démarrer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]