# SPEC - EducAmbiental

## 1. Descripción del Proyecto
EducAmbiental es una plataforma diseñada para fomentar la educación ambiental y el reciclaje a través de un sistema de gamificación. Los usuarios pueden aprender sobre materiales reciclables, localizar centros de reciclaje, completar módulos interactivos para ganar puntos y canjear dichos puntos por recompensas y códigos de descuento.

## 2. Arquitectura del Sistema
El proyecto sigue una arquitectura de n-capas (N-Tier Layered Architecture) utilizando el framework **Spring Boot**.

### Capas:
- **Controladores (REST API):** Gestionan las solicitudes HTTP y definen los endpoints.
- **Servicios:** Contienen la lógica de negocio y coordinan las operaciones entre repositorios.
- **Repositorios (Data Access):** Interfaces de Spring Data JPA para la interacción con la base de datos PostgreSQL.
- **Entidades:** Representación del modelo de datos.
- **DTOs (Data Transfer Objects):** Para la transferencia de datos entre capas y hacia el cliente, evitando exponer entidades directamente.
- **Mappers:** Uso de MapStruct para la conversión eficiente entre Entidades y DTOs.
- **Seguridad:** Implementación de JWT (JSON Web Tokens) para autenticación sin estado y Spring Security.

## 3. Requerimientos Funcionales (RF) y No Funcionales (RNF)
La documentación detallada de los requerimientos se encuentra organizada por áreas en el directorio `requirements/`. Puedes consultar el índice principal en [REQUIREMENTS.md](REQUIREMENTS.md).

### Áreas Principales:
- [Gestión de Usuarios y Seguridad](requirements/USUARIOS.md) (RF1 - RF5)
- [Catálogo de Residuos e Instrucciones](requirements/CATALOGO.md) (RF6 - RF9)
- [Centros de Acopio y Geolocalización](requirements/CENTROS.md) (RF10 - RF12)
- [Gamificación, Puntos e Impacto](requirements/GAMIFICACION.md) (RF13 - RF17)
- [Comunicación y Administración Global](requirements/COMUNICACION_ADMIN.md) (RF18 - RF20)
- [Aspectos Técnicos y UI/UX](requirements/TECNICO_UI.md) (RF21 - RF22, RNF1 - RNF8)

## 4. Stack Tecnológico
- **Lenguaje:** Java 21
- **Framework Principal:** Spring Boot 3.2.4
- **Persistencia:** Spring Data JPA + Hibernate
- **Base de Datos:** PostgreSQL
- **Seguridad:** Spring Security + JJWT
- **Documentación:** SpringDoc / OpenAPI (Swagger)
- **Migración de DB:** Flyway
- **Mapeo de Objetos:** MapStruct
- **Productividad:** Lombok

## 5. Modelo de Datos (Entidades Principales)
- `Usuario`: ID (UUID), nombre, correo, password, puntos, rol, nivel actual.
- `Material`: Nombre, instrucciones, categoría.
- `CentroReciclaje`: Ubicación, contacto, horarios, administrador (Usuario), capacidad (estado).
- `ModuloInteractivo`: Título, descripción, puntos otorgados, usuarios que lo completaron.
- `Recompensa`: Descripción, costo en puntos, stock.
- `CodigoDescuento`: Código único, estado, relación usuario/recompensa.
- `HistorialPuntos`: Transacciones, motivo, fecha.
- `Insignia`: Logros visuales para los usuarios.
- `VisitaEntrega`: Registro de kilogramos entregados por usuario en un centro.
- `ContenidoEstatico`: Título, cuerpo, tipo (Noticia/Info), autor.
- `Notificacion`: Mensaje, estado de lectura, fecha.

---
*Este documento sirve como guía para el desarrollo basado en especificaciones (Spec Driven Development) de EducAmbiental.*
