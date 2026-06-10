-- Imagen alusiva por material (reutilizable por las guías de la misma categoría).
-- IF NOT EXISTS: idempotente aunque la columna ya se haya creado manualmente en Supabase.
ALTER TABLE materiales ADD COLUMN IF NOT EXISTS imagen_url TEXT;

-- Las guías (contenidos_estaticos tipo='GUIA') se asocian a una categoría de residuo.
-- La imagen mostrada en la app se toma de un material de esa categoría.
ALTER TABLE contenidos_estaticos
    ADD COLUMN IF NOT EXISTS id_categoria BIGINT REFERENCES categorias_residuo(id) ON DELETE SET NULL;

-- Backfill de las guías existentes según el tema de su título.
UPDATE contenidos_estaticos SET id_categoria = (SELECT id FROM categorias_residuo WHERE nombre = 'PLÁSTICO')
 WHERE tipo = 'GUIA' AND id_categoria IS NULL AND (titulo ILIKE '%plástic%' OR titulo ILIKE '%plastic%');

UPDATE contenidos_estaticos SET id_categoria = (SELECT id FROM categorias_residuo WHERE nombre = 'PAPEL Y CARTÓN')
 WHERE tipo = 'GUIA' AND id_categoria IS NULL AND (titulo ILIKE '%papel%' OR titulo ILIKE '%cartón%' OR titulo ILIKE '%carton%');

UPDATE contenidos_estaticos SET id_categoria = (SELECT id FROM categorias_residuo WHERE nombre = 'VIDRIO')
 WHERE tipo = 'GUIA' AND id_categoria IS NULL AND titulo ILIKE '%vidrio%';

UPDATE contenidos_estaticos SET id_categoria = (SELECT id FROM categorias_residuo WHERE nombre = 'METAL')
 WHERE tipo = 'GUIA' AND id_categoria IS NULL AND (titulo ILIKE '%metal%' OR titulo ILIKE '%aluminio%' OR titulo ILIKE '%lata%');

UPDATE contenidos_estaticos SET id_categoria = (SELECT id FROM categorias_residuo WHERE nombre = 'MULTICAPA (TETRA PAK)')
 WHERE tipo = 'GUIA' AND id_categoria IS NULL AND (titulo ILIKE '%tetra%' OR titulo ILIKE '%multicapa%');
