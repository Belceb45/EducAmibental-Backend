# Comunicación y Administración Global (RF18 – RF20)

Notificaciones, internacionalización y métricas administrativas.

| RF | Descripción | Endpoints / Implementación | Estado |
|----|-------------|----------------------------|--------|
| RF18 | Notificaciones internas | `GET /api/notificaciones/usuario/{id}`, `.../no-leidas/count`, `PATCH /{id}/leer`, `PATCH /usuario/{id}/leer-todas` | ✅ |
| RF19 | Dashboard administrativo | `GET /api/dashboard/admin` (ADMIN_SYSTEM) | ✅ (backend) |
| RF20 | Soporte multi-idioma (i18n) | App: `constants/i18n.ts` (ES/EN) | ✅ |

**Notificaciones automáticas**
- Se generan al ganar XP, subir de nivel, desbloquear insignia o canjear recompensa.
- La campana del encabezado de la app muestra un badge con el número de no leídas.

**Dashboard admin (RF19)**
- Métricas: totalUsuarios, usuariosVerificados, totalCentros, totalMateriales, totalContenidos, ticketsAbiertos, ticketsPorEstado.
- Operado vía API/Postman; la app móvil es ciudadano-only.
