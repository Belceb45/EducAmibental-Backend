# Centros de Acopio y Geolocalización

| ID | Nombre | Prioridad | Descripción |
|---|---|---|---|
| **RF10** | Mapa interactivo | Alta | Mostrar un mapa con la ubicación de centros de reciclaje cercanos utilizando el GPS del usuario. |
| **RF11** | Detalle de Centro de Acopio | Alta | Ficha informativa con dirección, horarios, contacto y lista de materiales aceptados. |
| **RF12** | Gestión de perfil de Centro | Media | Permite al Administrador de Centro actualizar horarios, contacto y marcar estado de capacidad (contenedores llenos). |

---

### Detalles Técnicos

#### RF10: Mapa interactivo
- **Entrada:** Ubicación GPS del usuario.
- **Salida:** Mapa con marcadores de centros cercanos.
- **Precondición:** Permisos de ubicación otorgados.

#### RF12: Gestión de capacidad
- **Impacto:** Los cambios en el estado de capacidad se reflejan en tiempo real para todos los usuarios ciudadanos en el mapa.
