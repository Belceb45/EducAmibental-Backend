CREATE TABLE usuarios (
    id UUID PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    correo VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    puntos_actuales INTEGER NOT NULL DEFAULT 0,
    rol VARCHAR(50) NOT NULL DEFAULT 'USER'
);

CREATE TABLE categorias_residuo (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255)
);

CREATE TABLE centros_reciclaje (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    latitud DOUBLE PRECISION,
    longitud DOUBLE PRECISION,
    direccion VARCHAR(255),
    horario VARCHAR(255),
    contacto VARCHAR(255)
);

CREATE TABLE modulos_interactivos (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descripcion TEXT,
    puntos_otorgados INTEGER NOT NULL
);

CREATE TABLE recompensas (
    id BIGSERIAL PRIMARY KEY,
    tienda_ficticia VARCHAR(255),
    descripcion TEXT,
    costo_puntos INTEGER NOT NULL,
    stock INTEGER NOT NULL
);

CREATE TABLE materiales (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    instrucciones_reciclaje TEXT,
    id_categoria BIGINT REFERENCES categorias_residuo(id)
);

CREATE TABLE codigos_descuento (
    id BIGSERIAL PRIMARY KEY,
    id_recompensa BIGINT NOT NULL REFERENCES recompensas(id),
    codigo_alfanumerico VARCHAR(255) NOT NULL UNIQUE,
    id_usuario UUID REFERENCES usuarios(id),
    estado VARCHAR(50) NOT NULL DEFAULT 'DISPONIBLE'
);

CREATE TABLE historial_puntos (
    id BIGSERIAL PRIMARY KEY,
    id_usuario UUID NOT NULL REFERENCES usuarios(id),
    cantidad INTEGER NOT NULL,
    tipo_operacion VARCHAR(50) NOT NULL,
    motivo VARCHAR(255),
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
