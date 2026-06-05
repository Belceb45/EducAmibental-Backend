-- Script para añadir columnas faltantes que Hibernate no creó correctamente
DO $$ 
BEGIN 
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name='usuarios' AND column_name='enabled') THEN
        ALTER TABLE usuarios ADD COLUMN enabled BOOLEAN NOT NULL DEFAULT FALSE;
    END IF;
END $$;
