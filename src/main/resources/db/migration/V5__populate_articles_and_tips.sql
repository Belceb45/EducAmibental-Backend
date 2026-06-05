-- Asegurar que la columna autor exista
ALTER TABLE contenidos_estaticos ADD COLUMN IF NOT EXISTS autor VARCHAR(255);

-- Guías Detalladas por Clasificación (Basadas en Manuales)
INSERT INTO contenidos_estaticos (titulo, cuerpo, tipo, autor) VALUES 
('Guía Completa: Clasificación de Plásticos', 'Existen 7 tipos de plásticos identificados por el triángulo de Möbius. 1. PET (Botellas), 2. PEAD (Detergentes), 3. PVC (Tuberías), 4. PEBD (Bolsas), 5. PP (Tapers), 6. PS (Unicel), 7. Otros. Solo el 1, 2, 4 y 5 son altamente reciclables en la mayoría de centros.', 'GUIA', 'Admin EducAmbiental'),
('Manual de Papel y Cartón', 'Para que el papel sea reciclable debe estar SECO y LIMPIO. El cartón de pizza con grasa NO se recicla (va a orgánico). Las grapas no son problema, pero las cintas adhesivas deben retirarse.', 'GUIA', 'Admin EducAmbiental'),
('El Vidrio: El material infinito', 'El vidrio puede reciclarse infinitas veces sin perder calidad. Separa por colores (transparente, ámbar, verde) para facilitar el proceso. NUNCA mezcles cristal de ventanas o espejos con botellas.', 'GUIA', 'Admin EducAmbiental');

-- Tips y Recomendaciones Generales
INSERT INTO contenidos_estaticos (titulo, cuerpo, tipo, autor) VALUES 
('Tip del Día: Aplasta y Gana', 'Aplastar tus botellas de plástico y latas de aluminio ahorra hasta un 50% de espacio. Esto reduce la huella de carbono al optimizar el transporte de residuos.', 'TIP', 'EducBot'),
('¿Sabías qué? - El mito del enjuague', 'No necesitas lavar tus recipientes con jabón y mucha agua. Un enjuague rápido con agua de reuso es suficiente para quitar los restos de comida.', 'TIP', 'EducBot'),
('Regla de las 3R', 'Recuerda el orden de importancia: REDUCIR (evitar comprar innecesariamente), REUTILIZAR (dar un nuevo uso) y al final RECICLAR.', 'TIP', 'EducBot');

-- Artículos Destacados
INSERT INTO contenidos_estaticos (titulo, cuerpo, tipo, autor) VALUES 
('Economía Circular en el Hogar', 'La economía circular busca que nada se convierta en basura. Desde comprar a granel hasta reparar tus electrodomésticos, cada acción cuenta para cerrar el ciclo.', 'ARTICULO', 'Especialista Ambiental'),
('Cómo iniciar tu propia Composta', 'El 40% de nuestra basura es orgánica. Aprende a separar tus restos de fruta y verdura para crear abono natural para tus plantas.', 'ARTICULO', 'Especialista Ambiental');
