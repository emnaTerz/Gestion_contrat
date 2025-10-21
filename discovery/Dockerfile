# Étape 1 : Build Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copier pom.xml et télécharger les dépendances
COPY pom.xml .

# Copier le code source et compiler
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : Image runtime légère
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copier le jar compilé depuis l'étape build
COPY --from=build /app/target/*.jar app.jar

# Exposer le port du Discovery Server
EXPOSE 8761

# Démarrer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
