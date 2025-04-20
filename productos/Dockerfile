# Usa una imagen de  Java 17
FROM eclipse-temurin:17-jdk-alpine

# Crear directorio dentro del contenedor
WORKDIR /app

# Copiar el archivo jar al contenedor
COPY target/*.jar app.jar

# Exponer el puerto 
EXPOSE 8081

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
