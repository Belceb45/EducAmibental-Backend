-- Add detailed columns to categorias_residuo
ALTER TABLE categorias_residuo ADD COLUMN IF NOT EXISTS instrucciones_generales TEXT;
ALTER TABLE categorias_residuo ADD COLUMN IF NOT EXISTS icono VARCHAR(255);
ALTER TABLE categorias_residuo ADD COLUMN IF NOT EXISTS color VARCHAR(50);
