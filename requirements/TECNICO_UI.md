# Aspectos Técnicos y UI/UX (RF21 – RF22, RF25 – RF27, RNF)

| RF | Descripción | Implementación | Estado |
|----|-------------|----------------|--------|
| RF21 | Eliminación de cuenta | `DELETE /api/usuarios/mi-cuenta` + doble confirmación en la app | ✅ |
| RF22 | Visualización offline | `useNetworkStatus` + `OfflineView` (cacheo de contenido crítico, incl. centros del mapa en `SecureStore`) | ✅ |
| RF25 | Tema claro/oscuro | `ThemeContext`: sigue el esquema del sistema y persiste la preferencia manual en `SecureStore` | ✅ |
| RF26 | Inicio personalizado | `GET /api/dashboard/inicio`: resumen de progreso del usuario + tip del día en la pantalla principal | ✅ |
| RF27 | Páginas informativas | Pantallas de FAQ, Acerca de, Términos y Contacto en la app | ⚠️ Parcial: el formulario de Contacto aún no envía a `POST /api/tickets` |

## Requerimientos No Funcionales

| ID | Descripción | Implementación |
|----|-------------|----------------|
| RNF-01 | Interfaz intuitiva (UX/UI) | Componentes reutilizables, tema central (`constants/theme.ts`, `auth-styles.ts`); el panel admin reutiliza la paleta de la app |
| RNF-02 | Rendimiento | Marcadores de mapa memoizados (`tracksViewChanges` apagado tras rasterizar); paginación en listados |
| RNF-03 | Seguridad | BCrypt + Spring Security + JWT; `@PreAuthorize` por rol en endpoints administrativos |
| RNF-04 | Disponibilidad | Arquitectura sin estado (JWT) lista para escalar |
| RNF-05 | Integridad de datos | Sincronización con CDMX, OpenFoodFacts y OSM (Overpass/geocodificación) |
| RNF-06 | Escalabilidad | Arquitectura N-Tier modular (controladores/servicios/repos) |
| RNF-07 | Mantenibilidad | DTOs + MapStruct + documentación (`POSTMAN_GUIDE.md`, `requirements/`) |

## Stack
- **Backend:** Java 21, Spring Boot 3.2, PostgreSQL, Flyway, Spring Data JPA, MapStruct, Lombok, Spring Security + JJWT, SpringDoc/OpenAPI.
- **App móvil:** Expo SDK 54 / React Native 0.81, TypeScript, expo-router, react-native-maps, expo-location, expo-camera, expo-secure-store, i18next, Reanimated.
- **Panel admin:** React 19, Vite, react-router-dom 7, fetch + JWT (sin librerías de UI externas).
