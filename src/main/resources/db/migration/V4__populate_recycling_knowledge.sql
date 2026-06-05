-- Categorías Principales
INSERT INTO categorias_residuo (nombre, descripcion) VALUES 
('PLÁSTICO', 'Materiales poliméricos como botellas PET, envases de detergente, bolsas, etc.'),
('VIDRIO', 'Botellas, frascos y tarros de vidrio transparente o de color.'),
('PAPEL Y CARTÓN', 'Hojas de papel, cajas de cartón, periódicos y revistas.'),
('METAL', 'Latas de aluminio, conservas de acero y chatarra ferrosa.'),
('MULTICAPA (TETRA PAK)', 'Envases de cartón, plástico y aluminio para leche o jugos.'),
('ORGÁNICO', 'Restos de comida, cáscaras y desechos biodegradables.'),
('OTROS / NO RECICLABLES', 'Residuos sanitarios, colillas de cigarro, papel carbón, etc.');

-- Materiales Específicos e Instrucciones (Basado en Manuales de Clasificación)
-- PLÁSTICO
INSERT INTO materiales (nombre, instrucciones_reciclaje, id_categoria) VALUES 
('PET (Polietileno Tereftalato)', '1. Vaciar el contenido por completo. 2. Enjuagar con un poco de agua. 3. Escurrir. 4. Aplastar para reducir espacio. 5. Depositar con tapa en el contenedor de PLÁSTICO.', 1),
('PEAD (Polietileno de Alta Densidad)', '1. Retirar etiquetas si es posible. 2. Enjuagar para eliminar residuos de producto (detergentes, lácteos). 3. Secar. 4. Depositar en el contenedor de PLÁSTICO.', 1),
('PEBD (Polietileno de Baja Densidad)', '1. Asegurarse de que esté limpio y seco. 2. Juntar varias bolsas dentro de una sola para evitar que se dispersen. 3. Depositar en el contenedor de PLÁSTICO.', 1),
('PP (Polipropileno)', '1. Limpiar restos de comida o grasa. 2. Secar. 3. Los recipientes rígidos (tapers) pueden reutilizarse o depositarse en el contenedor de PLÁSTICO.', 1);

-- VIDRIO
INSERT INTO materiales (nombre, instrucciones_reciclaje, id_categoria) VALUES 
('Botella / Frasco de Vidrio', '1. Retirar tapas de metal o plástico. 2. Enjuagar el interior. 3. Escurrir. 4. No romper el envase. 5. Depositar en el contenedor de VIDRIO (No confundir con cerámica o cristal de ventana).', 2);

-- PAPEL Y CARTÓN
INSERT INTO materiales (nombre, instrucciones_reciclaje, id_categoria) VALUES 
('Cajas de Cartón', '1. Retirar cintas adhesivas y grapas metálicas. 2. Desarmar o doblar para que queden planos. 3. Mantener seco y libre de grasa. 4. Depositar en el contenedor de PAPEL/CARTÓN.', 3),
('Papel Blanco / Periódico', '1. Asegurarse de que no esté mojado ni sucio con comida. 2. No es necesario quitar grapas pequeñas. 3. Depositar en el contenedor de PAPEL/CARTÓN.', 3);

-- METAL
INSERT INTO materiales (nombre, instrucciones_reciclaje, id_categoria) VALUES 
('Latas de Aluminio (Refresco)', '1. Vaciar por completo. 2. No es necesario enjuagar si está seco. 3. Aplastar preferentemente. 4. Depositar en el contenedor de METAL.', 4),
('Latas de Conserva (Acero)', '1. Lavar para eliminar restos de grasa o alimento. 2. Introducir la tapa metálica dentro de la lata si es posible. 3. Depositar en el contenedor de METAL.', 4);

-- TETRA PAK
INSERT INTO materiales (nombre, instrucciones_reciclaje, id_categoria) VALUES 
('Envase Multicapa', '1. Desdoblar las esquinas. 2. Enjuagar y escurrir. 3. Aplastar para que quede totalmente plano. 4. Depositar en el contenedor de MULTICAPA.', 5);
