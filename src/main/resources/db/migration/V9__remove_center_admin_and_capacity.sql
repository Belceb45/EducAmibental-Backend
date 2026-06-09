-- Remove administrator and capacity columns from centros_reciclaje
ALTER TABLE centros_reciclaje DROP COLUMN IF EXISTS id_admin;
ALTER TABLE centros_reciclaje DROP COLUMN IF EXISTS capacidad_llena;
