# Aspectos Técnicos y UI/UX (RF21 – RF22, RNF)

| RF | Descripción | Implementación | Estado |
|----|-------------|----------------|--------|
| RF21 | Eliminación de cuenta | `DELETE /api/usuarios/mi-cuenta` + doble confirmación en la app | ✅ |
| RF22 | Visualización offline | `useNetworkStatus` + `OfflineView` (cacheo de contenido crítico) | ✅ |

## Requerimientos No Funcionales

| ID | Descripción | Implementación |
|----|-------------|----------------|
| RNF-01 | Interfaz intuitiva (UX/UI) | Componentes reutilizables, tema central (`constants/theme.ts`, `auth-styles.ts`) |
| RNF-02 | Rendimiento | Marcadores de mapa memoizados; paginación en listados |
| RNF-03 | Seguridad | BCrypt + Spring Security + JWT |
| RNF-04 | Disponibilidad | Arquitectura sin estado (JWT) lista para escalar |
| RNF-05 | Integridad de datos | Sincronización con CDMX y OpenFoodFacts |
| RNF-06 | Escalabilidad | Arquitectura N-Tier modular (controladores/servicios/repos) |
| RNF-07 | Mantenibilidad | DTOs + MapStruct + documentación (`POSTMAN_GUIDE.md`, `requirements/`) |

## Stack
- **Backend:** Java 21, Spring Boot 3.2, PostgreSQL, Flyway, MapStruct, JWT.
- **App:** Expo / React Native, TypeScript, expo-router, i18next.
