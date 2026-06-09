-- Add descripcion column to centros_reciclaje
ALTER TABLE centros_reciclaje ADD COLUMN IF NOT EXISTS descripcion TEXT;
