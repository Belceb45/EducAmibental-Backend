# Comunicación y Administración Global

| ID | Nombre | Prioridad | Descripción |
|---|---|---|---|
| **RF18** | Sistema de Notificaciones | Media | Envío de alertas, recordatorios, felicitaciones por logros y avisos de cierre de centros. |
| **RF19** | Dashboard administrativo | Alta | Métricas globales para el Admin del Sistema: usuarios activos, toneladas recicladas, materiales más procesados. |
| **RF20** | Gestión de contenido ampliada | Alta | Interfaz administrativa para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre Tips, Artículos y Guías Educativas. |
| **RF21** | Gestión de Catálogo y Materiales | Alta | El Administrador puede gestionar el catálogo de materiales, sus instrucciones de reciclaje y categorías asociadas. |
| **RF22** | Gestión de Recompensas y Stock | Alta | El Administrador puede crear recompensas, definir costos en puntos y gestionar el stock/códigos disponibles. |
| **RF23** | Gestión de Centros de Acopio | Alta | Capacidad de crear, editar o eliminar centros de acopio manualmente, además de la sincronización automática. |

---

### Detalles Técnicos

#### RF18: Sistema de Notificaciones
- **Entrada:** Evento disparador en el backend (ej. 15 días sin reciclar, nuevo logro obtenido).
- **Salida:** Mensaje de alerta visible en la interfaz o dispositivo del usuario.
- **Precondición:** El usuario debe estar registrado.

#### RF19: Dashboard administrativo
- **Entrada:** Número de usuarios activos, toneladas de residuos recicladas por mes.
- **Salida:** Métricas con el impacto global tomando en cuenta los materiales más procesados.
- **Descripción:** Si el administrador solicita la página de dashboard, esta debe generar con la última snapshot de datos las métricas.

#### RF20: Gestión de contenido ampliada
- **Descripción:** Cualquier usuario autorizado deberá ser capaz de crear, leer, actualizar y eliminar el contenido estático de la plataforma.
- **Postcondición:** El contenido editado se refleja en el sistema sin intervención en el código fuente.
