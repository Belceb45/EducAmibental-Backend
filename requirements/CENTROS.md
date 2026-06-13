# Centros de Acopio y Geolocalización (RF10 – RF12, RF24)

Localización de centros de reciclaje y soporte al usuario.

| RF | Descripción | Endpoints / Implementación | Estado |
|----|-------------|----------------------------|--------|
| RF10 | Mapa interactivo de centros (sync CDMX) | `GET /api/centros`, `POST /api/centros/sincronizar` | ✅ |
| RF11 | Detalle de centro (contacto, materiales) | `CentroReciclajeResponseDto` (lat/lon, dirección, horario, descripción, imagenUrl); callout con "Cómo llegar" en la app | ✅ |
| RF12 | Gestión de Tickets de soporte | `POST /api/tickets/usuario/{id}`, `GET /api/tickets`, `PATCH /api/tickets/{id}/asignar/{idAdmin}`, `PATCH /api/tickets/{id}/estado` | ✅ (gestión vía Panel Admin, RF23) |
| RF24 | Puntos de acopio externos (OSM/Overpass) | App: consulta a `overpass-api.de` en radio de 5 km (farmacias, contenedores de vidrio/plástico/e-waste/ropa, chatarrerías, segunda mano), clasificación por etiquetas `recycling:*`, filtros por categoría | ✅ (app) |

**Importación / Sincronización (ADMIN_SYSTEM)**
- `POST /api/centros/sincronizar`: API CKAN de Datos Abiertos CDMX.
- `POST /api/centros/importar-csv`: CSV local con geocodificación OSM.
- CRUD de centros disponible en el Panel de Administración Web (sección Centros).

**Notas**
- Los centros propios se cachean en la app (`SecureStore`) para el modo offline (RF22); los puntos OSM solo se consultan online.
- El mapa usa Google Maps en Android y Apple Maps en iOS (Expo Go en iOS no incluye el SDK de Google Maps).
- "Cómo llegar" abre la app de mapas nativa (Apple/Google Maps) mediante deep link.
