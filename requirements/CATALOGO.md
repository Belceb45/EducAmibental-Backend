# Catálogo de Residuos e Instrucciones (RF6 – RF9)

Catálogo de materiales, guías de disposición y escaneo de productos.

| RF | Descripción | Endpoints / Implementación | Estado |
|----|-------------|----------------------------|--------|
| RF6 | Catálogo de residuos por categorías | `GET /api/categorias`, `GET /api/materiales` | ✅ |
| RF7 | Instrucciones de disposición | `Material.instruccionesReciclaje`, `CategoriaResiduo.instruccionesGenerales` | ✅ |
| RF8 | Búsqueda predictiva | `GET /api/materiales/buscar?nombre=...` | ✅ |
| RF9 | Escaneo de productos (OpenFoodFacts) | `GET /api/scanner/{barcode}` | ✅ |

**Gestión de contenido (ADMIN_CONTENT)**
- `POST/PUT/DELETE /api/materiales` y `/api/categorias`.
- CRUD disponible en el Panel de Administración Web (RF23), secciones Materiales y Categorías.

**Notas**
- El escáner consulta OpenFoodFacts y mapea materiales a instrucciones sugeridas.
- En la app, el catálogo se consume desde la pestaña **Guía** y el escáner desde **Escanear**.
