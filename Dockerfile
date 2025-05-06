# Etapa 1: Descarga dependencias (cache útil para builds más rápidos)
FROM maven:3.8.5-openjdk-17-slim AS dependency
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Etapa 2: Build del proyecto
FROM dependency AS build
COPY src ./src
RUN mvn package -B -DskipTests

# Etapa 3: Imagen final ligera con solo Java y el .jar
FROM openjdk:17-jdk-alpine AS runtime
WORKDIR /app
COPY --from=build /app/target/server-0.0.1-SNAPSHOT.jar app.jar
COPY config.json .

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

