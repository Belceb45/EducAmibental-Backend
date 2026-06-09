-- Add imagen_url column to centros_reciclaje
ALTER TABLE centros_reciclaje ADD COLUMN IF NOT EXISTS imagen_url TEXT;
