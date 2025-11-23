# Configuración de Email para Reseteo de Contraseña

## Descripción

El sistema de reseteo de contraseña utiliza email para enviar tokens seguros a los usuarios. Este documento explica cómo configurar el servicio de email.

## Flujo de Reseteo de Contraseña

1. Usuario solicita reseteo en `/auth/forgot-password` con su email
2. Sistema genera un token seguro y lo guarda encriptado en la base de datos
3. Sistema envía email con enlace que contiene el token (no encriptado)
4. Usuario hace clic en el enlace y es redirigido a la página de reseteo
5. Usuario ingresa nueva contraseña en `/auth/reset-password` junto con el token
6. Sistema valida el token, actualiza la contraseña y marca el token como usado

## Configuración de Gmail (Desarrollo)

### Paso 1: Habilitar App Password en Gmail

1. Ve a tu cuenta de Google: https://myaccount.google.com/
2. En "Seguridad", habilita **Verificación en 2 pasos** si no lo tienes
3. Busca **Contraseñas de aplicaciones** (App Passwords)
4. Selecciona "Correo" y "Otro" (escribe "Parche Lector")
5. Copia la contraseña de 16 caracteres generada

### Paso 2: Configurar application-dev.properties

Edita `src/main/resources/application-dev.properties`:

```properties
# Mail Configuration (Development - using Gmail SMTP)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=tu-email@gmail.com
spring.mail.password=xxxx xxxx xxxx xxxx
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true
```

**IMPORTANTE:** 
- Reemplaza `tu-email@gmail.com` con tu email real
- Reemplaza `xxxx xxxx xxxx xxxx` con la App Password de 16 caracteres
- **NO USES tu contraseña normal de Gmail**, usa la App Password

### Paso 3: Configurar Frontend URL

En `src/main/resources/application.properties`:

```properties
# Application Configuration
app.frontend.url=http://localhost:5173
```

Esta URL se usa para construir el enlace de reseteo: `http://localhost:5173/reset-password?token=...`

## Configuración para Producción

Para producción, deberías usar un servicio profesional de email:

### Opción 1: SendGrid

```properties
spring.mail.host=smtp.sendgrid.net
spring.mail.port=587
spring.mail.username=apikey
spring.mail.password=TU_API_KEY_SENDGRID
```

### Opción 2: AWS SES

```properties
spring.mail.host=email-smtp.us-east-1.amazonaws.com
spring.mail.port=587
spring.mail.username=TU_SMTP_USERNAME
spring.mail.password=TU_SMTP_PASSWORD
```

### Opción 3: Mailgun

```properties
spring.mail.host=smtp.mailgun.org
spring.mail.port=587
spring.mail.username=postmaster@tu-dominio.mailgun.org
spring.mail.password=TU_API_KEY
```

## Estructura de la Base de Datos

La tabla `password_reset_tokens` almacena:

```sql
CREATE TABLE password_reset_tokens (
  id SERIAL PRIMARY KEY,
  user_id INTEGER NOT NULL,
  token_hash VARCHAR(255) UNIQUE NOT NULL,  -- Token encriptado con BCrypt
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  expires_at TIMESTAMP NOT NULL,             -- Expira en 1 hora
  used_at TIMESTAMP                          -- NULL si no se ha usado
);
```

## Seguridad

### ✅ Medidas Implementadas:

1. **Token seguro**: Generado con `SecureRandom` (32 bytes aleatorios)
2. **Token encriptado**: Se guarda con BCrypt en la base de datos
3. **Expiración**: Tokens expiran en 1 hora
4. **Uso único**: Tokens se marcan como usados después de cambiar contraseña
5. **Limpieza**: Tokens antiguos de un usuario se eliminan al solicitar uno nuevo
6. **Sin revelación**: La respuesta no indica si el email existe o no

### 🔒 Recomendaciones Adicionales:

- Implementa rate limiting en `/auth/forgot-password` (máximo 3 por hora)
- Usa HTTPS en producción
- Configura SPF, DKIM y DMARC en tu dominio de email
- Monitorea intentos fallidos de reseteo
- Considera agregar CAPTCHA para prevenir spam

## Testing

### Probar Solicitud de Reseteo:

```bash
curl -X POST http://localhost:8080/auth/forgot-password \
  -H "Content-Type: application/json" \
  -d '{"email":"ana@email.com"}'
```

### Probar Confirmación de Reseteo:

```bash
curl -X POST http://localhost:8080/auth/reset-password \
  -H "Content-Type: application/json" \
  -d '{"token":"TOKEN_DEL_EMAIL","newPassword":"nuevaPassword123"}'
```

### Verificar Email en Consola:

Si tienes problemas con el envío de emails, revisa los logs:

```
logging.level.org.springframework.mail=DEBUG
```

## Template del Email

El email se genera desde `src/main/resources/templates/password-reset-email.html` usando Thymeleaf.

Personaliza el template para cambiar:
- Estilos CSS
- Logo de la aplicación
- Textos y mensajes
- Footer y enlaces adicionales

## Frontend Integration

En tu aplicación Vue, crea una ruta para manejar el reseteo:

```typescript
// router/index.ts
{
  path: '/reset-password',
  name: 'ResetPassword',
  component: () => import('../views/ResetPasswordView.vue')
}
```

La vista debe:
1. Leer el token de la URL: `route.query.token`
2. Mostrar formulario para nueva contraseña
3. Enviar POST a `/auth/reset-password` con token y newPassword

## Troubleshooting

### Error: "Authentication failed"
- Verifica que estés usando App Password, no tu contraseña normal
- Asegúrate de que la verificación en 2 pasos esté habilitada

### Error: "Failed to send email"
- Revisa que el puerto 587 no esté bloqueado por firewall
- Verifica la configuración de SMTP en application-dev.properties

### Email no llega
- Revisa carpeta de spam
- Verifica que el email del usuario existe en la base de datos
- Revisa los logs de la aplicación para errores

### Token inválido o expirado
- Los tokens expiran en 1 hora
- Los tokens solo pueden usarse una vez
- Verifica que el token de la URL sea exacto (sin espacios)

## Variables de Entorno (Recomendado para Producción)

En lugar de hardcodear credenciales, usa variables de entorno:

```properties
spring.mail.username=${MAIL_USERNAME}
spring.mail.password=${MAIL_PASSWORD}
app.frontend.url=${FRONTEND_URL}
```

Luego configura en tu sistema:
```bash
export MAIL_USERNAME=tu-email@gmail.com
export MAIL_PASSWORD=xxxx-xxxx-xxxx-xxxx
export FRONTEND_URL=https://tu-dominio.com
```
