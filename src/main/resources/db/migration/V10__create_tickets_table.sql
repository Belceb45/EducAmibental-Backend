CREATE TABLE tickets (
    id UUID PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descripcion TEXT NOT NULL,
    estado VARCHAR(50) NOT NULL DEFAULT 'ABIERTO',
    prioridad VARCHAR(50) NOT NULL DEFAULT 'MEDIA',
    id_usuario_reporta UUID NOT NULL,
    id_admin_asignado UUID,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP,
    CONSTRAINT fk_usuario_reporta FOREIGN KEY (id_usuario_reporta) REFERENCES usuarios(id),
    CONSTRAINT fk_admin_asignado FOREIGN KEY (id_admin_asignado) REFERENCES usuarios(id)
);
