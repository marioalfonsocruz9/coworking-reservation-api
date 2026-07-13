# Coworking Reservation API

Microservicio REST para la gestión de reservas de espacios de coworking desarrollado como prueba técnica utilizando Java 17 y Spring Boot 3.

## Tecnologías

- Java 17
- Spring Boot 3
- Spring Security
- JWT Authentication
- Spring Data JPA
- PostgreSQL
- Docker
- Maven
- MapStruct
- Bean Validation (Jakarta Validation)
- Spring Cache
- Spring Actuator
- Resilience4j (Circuit Breaker)
- OpenAPI / Swagger

---

# Arquitectura

El proyecto está organizado por capas:

- Controller
- Service
- Repository
- Entity
- DTO
- Mapper
- Configuration
- Security
- Exception Handling

Se utiliza una arquitectura desacoplada basada en DTOs para evitar exponer directamente las entidades del dominio.

---

# Funcionalidades

## Autenticación

- Registro de usuarios
- Login mediante JWT
- Contraseñas almacenadas con BCrypt

## Espacios

- CRUD completo
- Acceso restringido por roles

## Reservas

- Crear reserva
- Consultar reservas propias
- Cancelar reservas
- Validación de solapamientos
- Administración completa para usuarios ADMIN

## Reportes

- Reporte de ocupación por rango de fechas
- Cache mediante Spring Cache

## Integraciones

- Simulación de validación de pago
- Circuit Breaker mediante Resilience4j
- Notificación asíncrona de confirmación

---

# Patrones utilizados

## Observer

Se implementó utilizando:

- ApplicationEventPublisher
- @EventListener

Cuando una reserva es confirmada se publica un evento de dominio que dispara de forma desacoplada el envío de una notificación.

Esta solución evita acoplar la lógica de reservas con la lógica de notificaciones.

---

# Circuit Breaker

La validación de pago se realiza contra un servicio externo simulado.

Se utiliza Resilience4j para:

- detectar fallos repetitivos
- abrir el circuito
- ejecutar un método fallback
- dejar la reserva en estado PENDING cuando el servicio no está disponible

El estado del circuito puede consultarse mediante Actuator.

---

# Seguridad

Spring Security + JWT

Roles soportados:

- ROLE_ADMIN
- ROLE_USER

---

# Actuator

Se encuentran habilitados:

- /actuator/health
- /actuator/info
- /actuator/metrics
- /actuator/circuitbreakers

---

# Configuración

Se utiliza ConfigurationProperties para centralizar la configuración de:

- JWT
- Servicio de pago

---

# Base de datos

PostgreSQL

La base de datos se ejecuta mediante Docker Compose.

---

# Ejecución del proyecto

## 1. Levantar PostgreSQL

```bash
docker compose up -d
```

Verificar que el contenedor se encuentre en ejecución:

```bash
docker ps
```

---

## 2. Compilar el proyecto

```bash
mvn clean install
```

---

## 3. Ejecutar la aplicación

Desde Maven:

```bash
mvn spring-boot:run
```

o directamente desde el IDE (Eclipse/IntelliJ).

El proyecto utiliza por defecto el perfil:

```
dev
```

---

# Swagger

Una vez iniciada la aplicación:

```
http://localhost:8080/swagger-ui/index.html
```


Importar la colección de Postman incluida en el proyecto y comenzar las pruebas.

---


# Usuarios de prueba

Al iniciar la aplicación con el perfil **dev**, se ejecuta un inicializador (`DevDataInitializer`) que crea automáticamente un usuario administrador para facilitar las pruebas.

### Administrador

```
Email: admin@coworking.com
Password: MyAdmin99*
Role: ROLE_ADMIN
```

> Sustituya los valores anteriores por las credenciales definidas en `DevDataInitializer`.

Los usuarios con rol **ROLE_USER** pueden registrarse mediante el endpoint:

```
POST /api/v1/auth/register
```

Las contraseñas se almacenan utilizando BCrypt.

> **Nota:** El usuario administrador solo se crea automáticamente cuando la aplicación se ejecuta con el perfil `dev`.

---

# Endpoints principales

## Autenticación

- POST /api/v1/auth/register
- POST /api/v1/auth/login

## Espacios

- GET /api/v1/spaces
- GET /api/v1/spaces/{id}
- POST /api/v1/spaces
- PUT /api/v1/spaces/{id}
- DELETE /api/v1/spaces/{id}

## Reservas

- POST /api/v1/reservations
- GET /api/v1/reservations/me
- GET /api/v1/reservations
- PATCH /api/v1/reservations/{id}/cancel

## Reportes

- GET /api/v1/reports/occupancy

---


# Postman Collection

El proyecto incluye una colección de Postman para facilitar la validación de todos los casos de uso implementados.

## Archivo

```
coworking-reservation-api.postman_collection.json
```

## Variables de la colección

Antes de ejecutar las peticiones configure las siguientes variables:

| Variable | Valor |
|----------|-------|
| `base_url` | `http://localhost:8080` |
| `api_prefix` | `/api/v1` |
| `auth_token` | Token JWT obtenido desde el endpoint de Login |

> **Nota:** La colección utiliza autenticación Bearer a nivel global. Una vez obtenido el JWT desde cualquiera de los endpoints de Login, copie el valor del campo `token` en la variable `auth_token` para autenticar automáticamente el resto de las peticiones.

---


# Decisiones de diseño

- Arquitectura por capas.
- DTOs para desacoplar la API del modelo de persistencia.
- Manejo centralizado de excepciones mediante ControllerAdvice.
- Validaciones mediante Jakarta Validation.
- Procesamiento asíncrono utilizando @Async.
- Publicación de eventos mediante ApplicationEventPublisher.
- Caché utilizando Spring Cache.
- Integración resiliente mediante Circuit Breaker.
- Configuración centralizada utilizando @ConfigurationProperties.


# Mejoras futuras

- Incorporar pruebas de integración con Testcontainers.
- Implementar versionado de la API.
- Integrar un proveedor de correo electrónico real.
- Sustituir el servicio de pago simulado por una integración real.
- Desplegar la aplicación completamente mediante Docker Compose incluyendo la API.