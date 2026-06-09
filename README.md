# EducAmbiental

## Descripcion del Proyecto
EducAmbiental es una plataforma integral orientada a la educacion ambiental y el fomento del reciclaje mediante tecnicas de gamificacion. El sistema permite a los ciudadanos localizar centros de acopio, acceder a guias detalladas sobre el manejo de residuos y participar en un ecosistema de recompensas basado en su participacion activa. El objetivo principal es transformar la percepcion del reciclaje de una obligacion a una actividad gratificante y cuantificable.

## Caracteristicas Principales

### Gestion de Residuos y Educacion
- Catalogo detallado de materiales reciclables clasificados por categorias.
- Guias de disposicion final con instrucciones de limpieza y separacion.
- Busqueda predictiva para la identificacion rapida de residuos.

### Geolocalizacion y Centros de Acopio
- Mapa interactivo con visualizacion de centros de reciclaje cercanos.
- Fichas informativas de centros incluyendo horarios, contacto y materiales aceptados.
- Sincronizacion automatica con Datos Abiertos CDMX e importacion por CSV (geocodificacion OSM).

### Sistema de Gamificacion (XP puro)
- Acumulacion de puntos de experiencia (XP) por completar modulos educativos (operacion idempotente).
- Sistema de niveles (1000 XP por nivel) e insignias que se otorgan automaticamente.
- Ranking comunitario y panel de impacto (`/api/usuarios/{id}/impacto`).

### Administracion y Metricas (via API / Postman)
- Gestion de contenidos, materiales, recompensas, modulos y tickets mediante roles `ADMIN_CONTENT` / `ADMIN_SYSTEM`.
- Dashboard administrativo (`/api/dashboard/admin`): usuarios, centros, materiales, contenidos y tickets por estado.
- Notificaciones internas automaticas (XP, nivel, insignias, canjes). La app movil es ciudadano-only.

### Experiencia Tecnica
- Arquitectura escalable basada en microservicios o n-capas segun la implementacion.
- Seguridad robusta mediante autenticacion basada en tokens (JWT) y roles de usuario.
- Diseño responsivo adaptable a dispositivos moviles y de escritorio.
- Soporte para visualizacion offline de datos criticos.

## Stack Tecnologico
- Lenguaje: Java 21
- Framework: Spring Boot 3.2.x
- Persistencia: Spring Data JPA / Hibernate
- Base de Datos: PostgreSQL
- Seguridad: Spring Security y JSON Web Tokens (JWT)
- Documentacion: OpenAPI / Swagger
- Gestion de Migraciones: Flyway

## Ejecucion
Requisitos: Java 21, Maven y una base de datos PostgreSQL accesible (configurable por variables de entorno `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`).

```bash
# Compilar
mvn compile

# Empaquetar (omitiendo tests que requieren BD)
mvn -DskipTests package

# Ejecutar
mvn spring-boot:run   # o: java -jar target/EducAmbiental-1.0-SNAPSHOT.jar
```
Flyway aplica las migraciones (`src/main/resources/db/migration`) al arrancar. La API queda en `http://localhost:8080`.

## Documentacion del Proyecto
Para mas detalles sobre el desarrollo y los requerimientos, consulte los siguientes archivos:
- SPEC.md: Especificaciones tecnicas y arquitectura.
- REQUIREMENTS.md: Indice de requerimientos funcionales y no funcionales.
- requirements/: Detalle de requerimientos por area (Usuarios, Catalogo, Centros, Gamificacion, Comunicacion/Admin, Tecnico/UI).
- POSTMAN_GUIDE.md: Guia exhaustiva de endpoints de la API.
