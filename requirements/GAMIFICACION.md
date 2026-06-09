# Gamificación, Puntos e Impacto (RF13 – RF17)

Aprendizaje gamificado con XP puro: nivel e insignias.

| RF | Descripción | Endpoints / Implementación | Estado |
|----|-------------|----------------------------|--------|
| RF13 | Gestión de Módulos/Trivias/Tips | `GET /api/modulos-educativos`, CRUD (ADMIN_CONTENT) | ✅ |
| RF14 | XP por completar actividades | `POST /api/usuarios/{id}/completar-actividad/{idModulo}` (idempotente) | ✅ |
| RF15 | Panel de impacto (nivel, insignias, puntos) | `GET /api/usuarios/{id}/impacto`, `GET /api/insignias/usuario/{id}` | ✅ |
| RF16 | Sistema de recompensas (canje) | `GET /api/recompensas`, `POST /api/recompensas/canjear` | ✅ |
| RF17 | Ranking comunitario | `GET /api/usuarios/ranking` | ✅ |

**Modelo de niveles**
- `nivel = puntosAcumulados / 1000 + 1` (constante `XP_POR_NIVEL = 1000`).
- Etiquetas: Eco-Aprendiz → Eco-Explorador → Eco-Guardián → Eco-Experto → Eco-Maestro.

**Insignias base** (seed `V15`)
- *Primer Módulo* (1er módulo), *Aprendiz Verde* (nivel ≥2), *Eco-Maestro* (nivel ≥5), *Coleccionista de XP* (≥1000 XP).

**Efectos automáticos al ganar XP**
1. Recalcular nivel · 2. Registrar `HistorialPuntos` · 3. Crear `Notificacion` · 4. Evaluar/otorgar insignias.

**Impacto = XP puro:** no se manejan métricas físicas estimadas (CO₂, agua, árboles, kg).
