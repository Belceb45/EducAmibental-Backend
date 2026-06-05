# Gestión de Usuarios y Seguridad

| ID | Nombre | Prioridad | Descripción |
|---|---|---|---|
| **RF1** | Registro de Usuario | Alta | El sistema permitirá a nuevos usuarios crear una cuenta proporcionando sus datos básicos. Se validará el formato del correo electrónico y se enviará un enlace o token para verificar la cuenta. |
| **RF2** | Inicio de Sesión | Alta | El sistema validará las credenciales del usuario (correo y contraseña) y permitirá el acceso a su perfil si son correctas. |
| **RF3** | Recuperación de Contraseña | Alta | El sistema permitirá al usuario recuperar el acceso a su cuenta mediante el envío de un enlace seguro o código de verificación al correo registrado. |
| **RF4** | Gestión de Roles | Alta | El sistema debe gestionar diferentes niveles de acceso: Usuario Ciudadano, Administrador de Centro y Administrador del Sistema. |
| **RF5** | Navegador de rutas | Alta | El sistema debe permitir la navegación fluida entre secciones, protegiendo las rutas privadas mediante validación de autenticación. |
| **RF6** | Eliminación de Cuenta | Media | El sistema permitirá al usuario eliminar su propia cuenta de forma permanente. Esto borrará sus datos personales y registros asociados según las políticas de privacidad. |

---

### Detalles Técnicos

#### RF1: Registro de Usuario
... (resto del contenido) ...

#### RF6: Eliminación de Cuenta
- **Entrada:** Confirmación de eliminación por parte del usuario.
- **Salida:** Cierre de sesión y eliminación de registros en la base de datos.
- **Precondición:** El usuario debe estar autenticado.
- **Postcondición:** El usuario ya no puede iniciar sesión con esas credenciales.
- **Entrada:** Correo electrónico, contraseña, datos personales (nombre, etc.).
- **Salida:** Confirmación de registro y envío de token o enlace de verificación al correo.
- **Precondición:** Acceder a "Regístrate aquí".
- **Postcondición:** Cuenta registrada y pendiente de verificación.

#### RF2: Inicio de Sesión
- **Entrada:** Correo electrónico, contraseña.
- **Salida:** Acceso al dashboard/página principal.
- **Precondición:** Tener una cuenta registrada.
- **Postcondición:** Sesión activa en el sistema.

#### RF4: Gestión de Roles
- **Niveles:** 
  1. **Usuario Ciudadano:** Uso general de la plataforma.
  2. **Administrador de Centro:** Gestión de un centro de acopio específico.
  3. **Administrador del Sistema:** Gestión global y métricas.
