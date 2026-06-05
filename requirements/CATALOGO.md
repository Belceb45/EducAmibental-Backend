# Catálogo de Residuos e Instrucciones

| ID | Nombre | Prioridad | Descripción |
|---|---|---|---|
| **RF6** | Navegador de categorías | Alta | El sistema debe mostrar un catálogo organizado de residuos (plástico, vidrio, papel, cartón, orgánicos y metales). |
| **RF7** | Instrucciones de disposición | Alta | Proporcionar guías claras sobre cómo separar, limpiar y desechar correctamente cada tipo de residuo. |
| RF8 | Búsqueda predictiva | Alta | Barra de búsqueda manual para identificar rápidamente categorías y métodos de reciclaje por nombre de producto. |
| **RF9** | Gestión de contenido (CRUD) | Alta | Permite al administrador crear, editar o eliminar información del catálogo para mantenerlo actualizado. |
| **RF23** | Identificación por Código de Barras | Media | El sistema permitirá escanear o ingresar el código de barras de un producto para consultar su información en OpenFoodFacts y sugerir instrucciones de reciclaje. |

---

### Detalles Técnicos

#### RF7: Instrucciones de disposición
- **Entrada:** Selección de una categoría de residuo.
- **Salida:** Información detallada sobre separación, limpieza y disposición.
- **Postcondición:** El usuario obtiene el conocimiento necesario para el manejo del residuo.

#### RF8: Búsqueda predictiva
- **Entrada:** Texto ingresado por el usuario.
- **Salida:** Resultados relacionados con el producto buscado.
- **Funcionalidad:** Mapeo de productos comunes a categorías específicas.

#### RF23: Identificación por Código de Barras
- **Entrada:** Código de barras (EAN/UPC).
- **Salida:** Información del producto y sugerencia de reciclaje basada en sus materiales componentes.
- **Integración:** Consumo de la API externa de OpenFoodFacts (GET https://world.openfoodfacts.org/api/v2/product/[barcode]).

