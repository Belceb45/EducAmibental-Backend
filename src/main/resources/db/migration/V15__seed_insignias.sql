-- Seed de insignias base para la gamificación (RF15).
-- Idempotente: solo inserta si no existe una insignia con el mismo nombre.
-- Los nombres deben coincidir con las constantes de GamificacionService.

INSERT INTO insignias (nombre, descripcion, icono_url)
SELECT 'Primer Módulo', 'Completaste tu primer módulo educativo.', '🌱'
WHERE NOT EXISTS (SELECT 1 FROM insignias WHERE nombre = 'Primer Módulo');

INSERT INTO insignias (nombre, descripcion, icono_url)
SELECT 'Aprendiz Verde', 'Alcanzaste el nivel 2 aprendiendo a reciclar.', '📗'
WHERE NOT EXISTS (SELECT 1 FROM insignias WHERE nombre = 'Aprendiz Verde');

INSERT INTO insignias (nombre, descripcion, icono_url)
SELECT 'Eco-Maestro', 'Llegaste al nivel 5. ¡Eres un referente ambiental!', '🏆'
WHERE NOT EXISTS (SELECT 1 FROM insignias WHERE nombre = 'Eco-Maestro');

INSERT INTO insignias (nombre, descripcion, icono_url)
SELECT 'Coleccionista de XP', 'Acumulaste 1000 puntos de experiencia.', '⭐'
WHERE NOT EXISTS (SELECT 1 FROM insignias WHERE nombre = 'Coleccionista de XP');
