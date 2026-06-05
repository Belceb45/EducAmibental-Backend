CREATE TABLE IF NOT EXISTS usuarios (
    id UUID PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    correo VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255),
    provider VARCHAR(50) NOT NULL DEFAULT 'LOCAL',
    puntos_actuales INTEGER NOT NULL DEFAULT 0,
    nivel_actual INTEGER NOT NULL DEFAULT 1,
    rol VARCHAR(50) NOT NULL DEFAULT 'USER',
    enabled BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS verification_codes (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    code VARCHAR(6) NOT NULL,
    expiry_date TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS categorias_residuo (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS centros_reciclaje (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    latitud DOUBLE PRECISION,
    longitud DOUBLE PRECISION,
    direccion VARCHAR(255),
    horario VARCHAR(255),
    contacto VARCHAR(255),
    capacidad_llena BOOLEAN DEFAULT FALSE,
    id_admin UUID REFERENCES usuarios(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS centro_materiales (
    id_centro BIGINT REFERENCES centros_reciclaje(id) ON DELETE CASCADE,
    id_categoria BIGINT REFERENCES categorias_residuo(id) ON DELETE CASCADE,
    PRIMARY KEY (id_centro, id_categoria)
);

CREATE TABLE IF NOT EXISTS modulos_interactivos (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descripcion TEXT,
    puntos_otorgados INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS usuario_modulo (
    id_modulo BIGINT REFERENCES modulos_interactivos(id) ON DELETE CASCADE,
    id_usuario UUID REFERENCES usuarios(id) ON DELETE CASCADE,
    PRIMARY KEY (id_modulo, id_usuario)
);

CREATE TABLE IF NOT EXISTS recompensas (
    id BIGSERIAL PRIMARY KEY,
    tienda_ficticia VARCHAR(255),
    descripcion TEXT,
    costo_puntos INTEGER NOT NULL,
    stock INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS materiales (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    instrucciones_reciclaje TEXT,
    id_categoria BIGINT REFERENCES categorias_residuo(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS codigos_descuento (
    id BIGSERIAL PRIMARY KEY,
    id_recompensa BIGINT NOT NULL REFERENCES recompensas(id) ON DELETE CASCADE,
    codigo_alfanumerico VARCHAR(255) NOT NULL UNIQUE,
    id_usuario UUID REFERENCES usuarios(id) ON DELETE CASCADE,
    estado VARCHAR(50) NOT NULL DEFAULT 'DISPONIBLE'
);

CREATE TABLE IF NOT EXISTS historial_puntos (
    id BIGSERIAL PRIMARY KEY,
    id_usuario UUID NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    cantidad INTEGER NOT NULL,
    tipo_operacion VARCHAR(50) NOT NULL,
    motivo VARCHAR(255),
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS contenidos_estaticos (
    id BIGSERIAL PRIMARY KEY,
    tipo VARCHAR(50) NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    cuerpo TEXT NOT NULL,
    id_autor UUID REFERENCES usuarios(id) ON DELETE SET NULL,
    fecha_publicacion TIMESTAMP
);

CREATE TABLE IF NOT EXISTS insignias (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    icono_url VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS usuario_insignia (
    id_insignia BIGINT REFERENCES insignias(id) ON DELETE CASCADE,
    id_usuario UUID REFERENCES usuarios(id) ON DELETE CASCADE,
    PRIMARY KEY (id_insignia, id_usuario)
);

CREATE TABLE IF NOT EXISTS notificaciones (
    id BIGSERIAL PRIMARY KEY,
    id_usuario UUID NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    mensaje TEXT NOT NULL,
    leida BOOLEAN DEFAULT FALSE,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS visitas_entregas (
    id BIGSERIAL PRIMARY KEY,
    id_usuario UUID NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    id_centro BIGINT NOT NULL REFERENCES centros_reciclaje(id) ON DELETE CASCADE,
    kilogramos_reciclados DOUBLE PRECISION NOT NULL,
    fecha_hora TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
