-- Add content and type columns to modulos_interactivos
ALTER TABLE modulos_interactivos ADD COLUMN IF NOT EXISTS contenido TEXT;
ALTER TABLE modulos_interactivos ADD COLUMN IF NOT EXISTS tipo VARCHAR(50);
