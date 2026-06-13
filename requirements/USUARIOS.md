# Usuarios y Seguridad (RF1 – RF5, RF21)

Gestión de cuentas, autenticación y control de acceso.

| RF | Descripción | Endpoints / Implementación | Estado |
|----|-------------|----------------------------|--------|
| RF1 | Registro con verificación por correo | `POST /api/auth/register`, `POST /api/auth/verify` (código de 6 dígitos) | ✅ |
| RF2 | Inicio de sesión (JWT) + Google Sign-In | `POST /api/auth/authenticate`, `POST /api/auth/google` | ✅ |
| RF3 | Recuperación de contraseña por código | `POST /api/auth/forgot-password`, `POST /api/auth/reset-password` | ✅ |
| RF4 | Gestión de roles (USER, ADMIN_SYSTEM, ADMIN_CONTENT) | `@PreAuthorize` + `Usuario.rol`; `GET /api/usuarios` restringido a `ADMIN_SYSTEM` | ✅ |
| RF5 | Protección de rutas y sesión | `SecurityConfig`, `JwtAuthenticationFilter`; en la app, redirección por estado de sesión | ✅ |
| RF21 | Eliminación de cuenta | `DELETE /api/usuarios/mi-cuenta`, `DELETE /api/usuarios/{id}` | ✅ |

**Notas**
- Las contraseñas se cifran con BCrypt (RNF-03).
- El registro crea la cuenta deshabilitada (`enabled=false`) hasta verificar el correo.
- Google Sign-In requiere el módulo nativo (development build); en Expo Go la app degrada con aviso, sin crashear.
- El login del Panel de Administración Web (RF23) rechaza cuentas `USER`; la gestión de usuarios (listar/eliminar) se hace desde su sección Usuarios.
