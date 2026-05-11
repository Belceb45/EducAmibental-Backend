# Gamificación, Puntos e Impacto

| ID | Nombre | Prioridad | Descripción |
|---|---|---|---|
| **RF13** | Check-in de Reciclaje | Alta | Validación de entrega física en el centro mediante código QR o geolocalización para evitar fraudes. |
| **RF14** | Cálculo de puntos XP | Alta | Ejecución de reglas de negocio para otorgar puntos por materiales entregados y recursos educativos completados. |
| **RF15** | Panel de Impacto Personal | Baja | Visualización de estadísticas: kg reciclados, nivel actual y equivalencias ecológicas. |
| **RF16** | Desbloqueo de Logros | Baja | Asignación de medallas/insignias visuales al alcanzar hitos específicos (ej. "Primera entrega"). |
| **RF17** | Ranking de Usuarios | Media | Tabla de clasificación (Top 10/50) para fomentar la competencia sana. |

---

### Detalles Técnicos

#### RF13: Check-in de Reciclaje
- **Entrada:** Escaneo de código QR.
- **Salida:** Confirmación de visita registrada.
- **Postcondición:** Se activa el proceso de asignación de puntos (RF14).

#### RF14: Lógica de Puntos
- **Ejemplo de Reglas:** 10 puntos por visita, 5 puntos por kilo de PET.
- **Postcondición:** Actualización del saldo total y posible subida de nivel.
