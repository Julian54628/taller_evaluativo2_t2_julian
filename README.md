# Taller Evaluativo - Proyecto

Descripción
-----------
API REST desarrollada con Spring Boot. Incluye:
- Spring Boot (Web, opcional MongoDB)
- OpenAPI / Swagger UI (springdoc)
- Tests con JUnit 5 y Mockito
- Cobertura de código con JaCoCo

Requisitos
---------
- Java 17+
- Maven 3.6+

Instalación y ejecución local
----------------------------
Clonar y construir:
```bash
git clone <repo-url>
cd taller-evaluativo
mvn clean verify
```

Ejecutar la aplicación:
```bash
mvn spring-boot:run
```
La API estará en http://localhost:8080 por defecto.

Swagger UI
----------
Local:
http://localhost:8080/swagger-ui.html

En producción (Azure) — reemplazar con el enlace real:
https://<tu-app-azure>.azurewebsites.net/swagger-ui.html

Ejemplos de endpoints
---------------------
GET /api/ejemplo
Response 200:
{
  "id": 1,
  "mensaje": "Hola mundo"
}

POST /api/ejemplo
Request:
{
  "nombre": "Julian"
}
Response 201:
{
  "id": 2,
  "nombre": "Julian",
  "creadoEn": "2025-10-21T12:34:56"
}

Reportes y cobertura
--------------------
- Reporte de JaCoCo generado en: target/site/jacoco/index.html
- Ejecutar `mvn verify` para generar reportes y cobertura.

Entrega en GitHub
-----------------
Cada estudiante debe incluir en el repositorio:
- Código fuente completo
- README con descripción, instrucciones, ejemplos de request/response y enlace al Swagger UI publicado en Azure
- Indicar en el README el enlace público de Swagger UI antes de entregar

