# Microservicios: Gestión de Productos e Inventarios 💻.
## Descripcion: 
Este proyecto implementa una arquitectura basada en microservicios usando Spring Boot, Docker y SQL Server. Se compone de dos microservicios: **Productos** e **Inventario**, los cuales se comunican entre sí mediante REST y autenticación por API Key.

## Instrucciones de instalación y ejecución 🚀:
### Requisitos previos: 
#### Antes de comenzar, debe asegurarse de tener instalados los siguientes programas en su sistema: 
- Java 17
- Maven
- Docker y Docker Compose
- SQL SERVER 2022
- SSMS (Sql Server Management Studio)
- Visual Studio Code.
- Git
-**Nota:** A continuacion encontrara dos maneras de ejecutar los microservicios, elija el que considere pertinente.

## Primer metodo de ejecución 🧩: 
 ### 1. Clonar el repositorio:
  Para obtener la copia del proyecto en su equipo, deberá abrir una terminal o PowerShell (tambien es recomendado hacerlo con la terminal de git) y ejecutar el siguiente comando: `git clone https://github.com/JuanArenas-Dev/Linktic.git`.
  
  Luego acceda al directorio del proyecto con el siguiente comando: `cd Linktic `, tenga en cuenta que este comando puede variar dependiendo del lugar en el que usted ejecute la terminal de comandos para clonar el repositorio, tambien podría usar el comando `cd C:\Users\"su_usuario"\Linktic` por si su sistema lo guardo de manera predeterminada.

### 2. Configurar la base de datos

Para que la aplicación funcione correctamente, es necesario configurar la base de datos en **SQL Server** siguiendo estos pasos:

####  1. Abrir SQL Server Management Studio (SSMS)

1. Debera abirir **SQL Server Management Studio (SSMS)**.  
2. En la ventana de conexión, ingrese los datos de su servidor SQL:  
   - **Servidor**: `localhost` (si está usando SQL Server localmente) o el nombre de su servidor.  
   - **Autenticación**:  
     - **Autenticación de SQL Server** ( usuario y contraseña, ingreselos aquí).  
3. Haga clic en **Conectar**.  

### 3. Crear las base de datos usando los scripts SQL  

Una vez conectado al servidor, siga estos pasos para ejecutar el script que crea la base de datos:  

**Microservicio productos:**
1. En **SSMS**, haga clic en **Nueva consulta** en la barra superior.  
2. Haga clic en **Archivo** → **Abrir** → **Archivo...** y seleccione el archivo `productosdb.sql` ubicado en la carpeta del repositorio clonado.   
3. Con el script abierto, presione **F5** o haga clic en el botón **Ejecutar**.  
4. Una vez finalizada la ejecución, debería ver un mensaje indicando que las tablas y los registros se crearon correctamente.

**Microservicio inventario:**
1. En **SSMS**, haga clic en **Nueva consulta** en la barra superior.  
2. Haga clic en **Archivo** → **Abrir** → **Archivo...** y seleccione el archivo `inventariodb.sql` ubicado en la carpeta del repositorio clonado.   
3. Con el script abierto, presione **F5** o haga clic en el botón **Ejecutar**.  
4. Una vez finalizada la ejecución, debería ver un mensaje indicando que las tablas y los registros se crearon correctamente.

### 4. Configurar la conexión 
**Aplica para ambos microservicios**

1. Abra **Visual Studio Code** y cargue el proyecto.  
2. Dirijase al archivo `productos/src/main/resources/aplication.properties` o `inventario/src/main/resources/aplication.properties` y edite el usuario y la contraseña:

 
```json
spring.datasource.username=" su usuario de conexion a sql server"
spring.datasource.password="su contraseña de conexion a sql server"
```
### 5. Ejecutar los microservicios 
**Aplica para ambos microservicios**

Para ejecutar los microservicios, siga estos pasos:  

1. En **Visual Studio Code**, Dirijase al archivo `productos/src/main/java/com/example/productos/ProductosAplication.java` o `inventario/src/main/java/com/example/inventario/InventarioAplication.java` dependiendo del microservicio que desee ejecutar, luego realice click derecho sobre el archivo y seleccione la opcion denominada como **Run Java**
2. Alternativamente, puede ejecutarlos desde la terminal con el siguiente comando:
```sh
mvn spring-boot:run
```
Los microservicios se ejecutaran y estarán disponibles en el navegador en:
- **Microservicio Productos:** http://localhost:8081/swagger-ui/index.html#/ 
- **Microservicio Inventario:** http://localhost:8082/swagger-ui/index.html#/


## Segundo metodo de ejecución 🧩: 
 ### 1. Clonar el repositorio:
  Para obtener la copia del proyecto en su equipo, deberá abrir una terminal o PowerShell (tambien es recomendado hacerlo con la terminal de git) y ejecutar el siguiente comando: `git clone https://github.com/JuanArenas-Dev/Linktic.git`.
  
  Luego acceda al directorio del proyecto con el siguiente comando: `cd Linktic `, tenga en cuenta que este comando puede variar dependiendo del lugar en el que usted ejecute la terminal de comandos para clonar el repositorio, tambien podría usar el comando `cd C:\Users\"su_usuario"\Linktic` por si su sistema lo guardo de manera predeterminada.
  
  ### 2. Ejecutar con Docker: 
  1. Compile los microservicios localmente, para esto, dirijase a la carpeta del repositorio o proyecto clonado (*Linktic*) abra la terminal y escriba el siguiente comando:
```bash
    cd productos
    ./mvnw clean package -DskipTests

    cd ../inventario
    ./mvnw clean package -DskipTests

    cd ..
  ```
  2. Luego desde la raiz del protyecto donde esta el archivo docker-compose.yml ejecute el siguiente comando:
     ```bash
       docker-compose up --build
      ```

 Esto levantará los siguientes servicios:
  - Microservicio de productos en el puerto 8081

  - Microservicio de inventario en el puerto 8082

  - Base de datos SQL Server en el puerto 1433

Para verificar que los contenedores estan ejecutandose, ejecute el siguiente comando: 
```bash
     docker ps
```
## Autenticación por API Key 🔐
Para hacer peticiones a los endpoints protegidos del microservicio de productos, debe incluir el siguiente header mediante Swagger. Por favor siga estos pasos:

1. Haga clic en el botón de color verde claro con simbolo de candado denominado como *"Authorize"* que se encuentra ubicado en la parte superior derecha.
2. En la ventana despegable en el campo de texto denominado como *"value"*, introduzca la siguiente clave: **apikey1234**

## Ejecutar pruebas unitarias y de integracion ⚙️: 

Para ejecutar las pruebas unitarias y de integracion donde se encuentran las pruebas de creacion y actualizacion de productos, comunicacion entre los microservicios y manejo de errores y fallos, debera ejecutar este comando en la terminal de la raiz del proyecto (*LinkTic*): 
```bash
    cd productos
    ./mvnw test

    cd ../inventario
    ./mvnw test
```

##  Decisiones Técnicas y Justificaciones 📃

| **Decisión**                    | **Justificación**                                                                                                                                                                                                                                                                                 |
|---------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Microservicios independientes   | Favorece la escalabilidad, el despliegue individual, y la separación de responsabilidades. Permite desarrollar, probar y escalar cada servicio de manera independiente, lo cual mejora la mantenibilidad.                                                  |
| Comunicación por REST (HTTP/JSON) | Se usó REST por su simplicidad, estandarización y compatibilidad con herramientas como Postman, Swagger, y navegadores. Además, facilita el debugging durante el desarrollo y pruebas locales.                                                           |
| API Key entre microservicios    | Se implementó un mecanismo básico de autenticación para asegurar que solo servicios autorizados se comuniquen entre sí, protegiendo los endpoints internos y agregando una capa mínima de seguridad sin complejidad adicional.                           |
| Manejo de timeout y reintentos  | Evita fallos en cadena ante microservicios inactivos o lentos, asegurando resiliencia y tolerancia a fallos temporales en la red o servicios dependientes.                                                                                                 |
| Docker                          | Facilita un entorno de ejecución unificado sin importar el sistema operativo. Mejora el despliegue, portabilidad y consistencia entre entornos (desarrollo, pruebas y producción).                                                                      |
| Docker Compose                  | Permite orquestar múltiples contenedores fácilmente, definiendo la red, servicios y dependencias en un solo archivo, lo que simplifica el despliegue de la solución completa.                                                                           |
| SQL Server                      | Base de datos robusta y madura, ampliamente usada en entornos corporativos. Ofrece buen rendimiento, soporte para relaciones complejas e integración sencilla con herramientas de análisis y administración como SSMS. Además, tiene imagen oficial en Docker. |
| Spring Boot                     | Framework de desarrollo rápido y opinionado, ideal para construir microservicios con una arquitectura limpia, control de dependencias con Maven y soporte para pruebas y documentación con Swagger.                                                      |
| Swagger / OpenAPI               | Documentación automática y visual de la API. Permite a otros desarrolladores y testers interactuar con los endpoints fácilmente, lo que mejora la usabilidad y colaboración.                                                                             |

     
## Diagrama de Interacción entre Servicios 💹

```text
┌────────────────┐       REST + API Key        ┌──────────────────┐
  INVENTARIO       ─────────────────────────▶      PRODUCTOS        
  puerto: 8082                                    puerto: 8081     
                  ◀────── Respuesta JSON ─────                 
└────────────────┘                            └──────────────────┘
         │                                          ▲
         │                                          │
         ▼                                          ▼
  Base de datos                              Base de datos
 (SQL Server)                                (SQL Server)
  puerto: 1433                                puerto: 1433


  

  


