# Comunicación y Administración Global (RF18 – RF20, RF23)

Notificaciones, internacionalización, métricas administrativas y panel web.

| RF | Descripción | Endpoints / Implementación | Estado |
|----|-------------|----------------------------|--------|
| RF18 | Notificaciones internas | `GET /api/notificaciones/usuario/{id}`, `.../no-leidas/count`, `PATCH /{id}/leer`, `PATCH /usuario/{id}/leer-todas` | ✅ |
| RF19 | Dashboard administrativo | `GET /api/dashboard/admin` (ADMIN_SYSTEM); UI en el Panel Admin (sección Dashboard) | ✅ |
| RF20 | Soporte multi-idioma (i18n) | App: `constants/i18n.ts` (ES/EN) con `i18next` / `react-i18next` | ✅ |
| RF23 | Panel de Administración Web | Proyecto `EducAmbientalAdmin` (React + Vite). CRUD completo contra la API real, validado además por `@PreAuthorize` en el backend | ✅ |

**Notificaciones automáticas**
- Se generan al ganar XP, subir de nivel, desbloquear insignia o canjear recompensa.
- La campana del encabezado de la app muestra un badge con el número de no leídas.

**Dashboard admin (RF19)**
- Métricas: totalUsuarios, usuariosVerificados, totalCentros, totalMateriales, totalContenidos, ticketsAbiertos, ticketsPorEstado.
- Visualizado en el Panel de Administración Web; Postman sigue disponible como cliente alternativo.

**Panel de Administración Web (RF23)**
- Login restringido a cuentas administrativas (un `USER` ciudadano es rechazado).
- `ADMIN_SYSTEM`: Dashboard · Tickets · Usuarios · Centros.
- `ADMIN_CONTENT`: Contenido · Módulos · Materiales · Categorías · Recompensas.
- Token JWT en `localStorage`; un `401` cierra la sesión automáticamente.
