# Utilisez une image OpenJDK 17 comme base
FROM openjdk:17

# Répertoire de travail dans le conteneur
WORKDIR /app

# Copie de l'ensemble du contenu du répertoire de travail
COPY . .

# Construction de l'application Maven
RUN mvn clean package

# Copie du JAR construit
COPY target/evae_backend-0.0.1-SNAPSHOT.jar .

# Exposition du port utilisé par votre application Spring Boot
EXPOSE 8082

# Commande de démarrage de l'application
CMD ["java", "-jar", "evae_backend-0.0.1-SNAPSHOT.jar"]

