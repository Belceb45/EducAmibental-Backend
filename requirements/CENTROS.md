# Centros de Acopio y Geolocalización (RF10 – RF12)

Localización de centros de reciclaje y soporte al usuario.

| RF | Descripción | Endpoints / Implementación | Estado |
|----|-------------|----------------------------|--------|
| RF10 | Mapa interactivo de centros (sync CDMX) | `GET /api/centros`, `POST /api/centros/sincronizar` | ✅ |
| RF11 | Detalle de centro (contacto, materiales) | `CentroReciclajeResponseDto` (lat/lon, dirección, horario, descripción, imagenUrl) | ✅ |
| RF12 | Gestión de Tickets de soporte | `POST /api/tickets/usuario/{id}`, `GET /api/tickets`, `PATCH /api/tickets/{id}/asignar/{idAdmin}`, `PATCH /api/tickets/{id}/estado` | ✅ (backend) |

**Importación / Sincronización (ADMIN_SYSTEM)**
- `POST /api/centros/sincronizar`: API CKAN de Datos Abiertos CDMX.
- `POST /api/centros/importar-csv`: CSV local con geocodificación OSM.

**Notas**
- RF12 (Tickets) se gestiona desde el backend/Postman (rol `ADMIN_SYSTEM`); la app permite al ciudadano reportar.
