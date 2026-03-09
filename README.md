# 🔧 TechRepair Backend API

Sistema de gestión de soporte técnico desarrollado con **Spring Boot**, **JWT** y **MariaDB**.

---

## 🛠️ Tecnologías utilizadas

- Java 11+
- Spring Boot 2.7.x
- Spring Security + JWT
- Spring Data JPA
- MariaDB
- Maven

---

## ⚙️ Configuración del entorno

### Variables de entorno requeridas

| Variable | Descripción | Ejemplo |
|---|---|---|
| `DB_PASSWORD` | Contraseña de la base de datos | `Mi_Clave123` |
| `JWT_SECRET` | Clave secreta para firmar tokens JWT | `MiClaveSecreta123` |

### Propiedades principales (`application.properties`)

| Propiedad | Valor |
|---|---|
| Puerto del servidor | `8080` |
| Base de datos | `techrepair` (MariaDB en `localhost:3306`) |
| Expiración JWT | `86400000 ms` (24 horas) |
| Tamaño máximo de archivos | `10MB` |

### Ejecutar el proyecto

```bash
# Establecer variables de entorno (Windows PowerShell)
$env:DB_PASSWORD="tu_contraseña"
$env:JWT_SECRET="tu_clave_secreta"

# Ejecutar
mvn spring-boot:run
```

---

## 🔐 Autenticación

Todos los endpoints (excepto `/api/auth/**`) requieren un token JWT en el header:

```
Authorization: Bearer <token>
```

El token se obtiene al hacer login. Expira en **24 horas**.

---

## 📋 Documentación de Servicios (API Endpoints)

### 1. 🔑 Autenticación — `/api/auth`

#### POST `/api/auth/register`
Registra un nuevo usuario en el sistema.

**Body (JSON):**
```json
{
  "email": "usuario@correo.com",
  "password": "contraseña123",
  "name": "Nombre Apellido",
  "role": "ADMIN"
}
```
> Roles disponibles: `ADMIN`, `TECHNICIAN`, `CLIENT`

**Respuesta exitosa `200 OK`:**
```json
{
  "success": true,
  "message": "Usuario registrado exitosamente",
  "data": { ... }
}
```

---

#### POST `/api/auth/login`
Inicia sesión y retorna un token JWT.

**Body (JSON):**
```json
{
  "email": "usuario@correo.com",
  "password": "contraseña123"
}
```

**Respuesta exitosa `200 OK`:**
```json
{
  "success": true,
  "data": {
    "token": "eyJhbGciOiJIUzUxMiJ9..."
  }
}
```

---

### 2. 🎫 Tickets — `/api/tickets`

#### POST `/api/tickets?clientId={id}`
Crea un nuevo ticket de soporte técnico.

**Parámetro:** `clientId` (Long) — ID del cliente que abre el ticket

**Body (JSON):**
```json
{
  "title": "Problema con el computador",
  "description": "El equipo no enciende",
  "priority": "HIGH"
}
```
> Prioridades disponibles: `LOW`, `MEDIUM`, `HIGH`

**Respuesta exitosa `200 OK`:** Objeto `Ticket` creado.

---

#### GET `/api/tickets`
Lista todos los tickets con paginación.

**Parámetros opcionales:** `page`, `size`, `sort`

**Ejemplo:** `/api/tickets?page=0&size=10`

**Respuesta exitosa `200 OK`:** Página de objetos `Ticket`.

---

#### GET `/api/tickets/{id}`
Obtiene un ticket específico por su ID.

**Respuesta exitosa `200 OK`:** Objeto `Ticket`.

**Respuesta `404 Not Found`:** Si el ticket no existe.

---

#### PUT `/api/tickets/{id}/assign?technicianId={id}`
Asigna un técnico a un ticket existente.

**Parámetros:**
- `id` (Long) — ID del ticket
- `technicianId` (Long) — ID del técnico a asignar

**Respuesta exitosa `200 OK`:** Objeto `Ticket` actualizado.

---

#### PUT `/api/tickets/{id}/close`
Cierra un ticket de soporte.

**Respuesta exitosa `200 OK`:** Objeto `Ticket` cerrado.

**Respuesta `404 Not Found`:** Si el ticket no existe.

---

### 3. 👥 Usuarios — `/api/users`

#### GET `/api/users`
Lista todos los usuarios con paginación.

**Parámetros opcionales:** `page`, `size`, `sort`

**Respuesta exitosa `200 OK`:** Página de objetos `User`.

---

#### POST `/api/users`
Crea un nuevo usuario.

**Body (JSON):**
```json
{
  "name": "Juan García",
  "email": "juan@correo.com",
  "password": "contraseña123",
  "role": "TECHNICIAN"
}
```

**Respuesta exitosa `200 OK`:**
```json
{
  "success": true,
  "data": { "id": 1, "name": "Juan García", "email": "juan@correo.com" }
}
```

---

#### PUT `/api/users/{id}`
Actualiza los datos de un usuario existente.

**Body (JSON):**
```json
{
  "name": "Juan García Actualizado",
  "email": "juannuevo@correo.com",
  "role": "ADMIN"
}
```

**Respuesta exitosa `200 OK`:** Objeto `UserResponse` actualizado.

---

#### DELETE `/api/users/{id}`
Elimina un usuario del sistema.

**Respuesta exitosa `200 OK`:**
```json
{
  "success": true,
  "message": "Usuario eliminado correctamente"
}
```

---

### 4. 🔌 Conexiones — `/api/connections`

#### POST `/api/connections?technicianId={id}`
Inicia una nueva sesión de conexión remota entre técnico y cliente.

**Parámetro:** `technicianId` (Long) — ID del técnico

**Body (JSON):**
```json
{
  "clientId": 5,
  "sessionId": "session-abc-123"
}
```

**Respuesta exitosa `200 OK`:** Objeto `Connection` creado.

---

#### GET `/api/connections/active`
Lista todas las conexiones activas en el sistema.

**Respuesta exitosa `200 OK`:** Lista de objetos `Connection`.

---

### 5. 🔔 Notificaciones — `/api/notifications`

#### GET `/api/notifications?userId={id}`
Lista todas las notificaciones de un usuario específico.

**Parámetro:** `userId` (Long) — ID del usuario

**Respuesta exitosa `200 OK`:** Lista de objetos `Notification`.

---

#### POST `/api/notifications`
Crea y envía una nueva notificación.

**Body (JSON):**
```json
{
  "userId": 3,
  "message": "Tu ticket ha sido asignado a un técnico",
  "type": "INFO"
}
```

**Respuesta exitosa `200 OK`:** Objeto `Notification` creado.

---

### 6. 📊 Dashboard — `/api/dashboard`

#### GET `/api/dashboard/stats`
Retorna estadísticas generales del sistema (tickets abiertos, cerrados, técnicos activos, etc.)

**Respuesta exitosa `200 OK`:** Objeto `DashboardStatsResponse` con métricas del sistema.

---

### 7. 📄 Reportes — `/api/reports`

#### GET `/api/reports`
Genera y descarga un reporte en formato PDF con el estado del sistema.

**Headers de respuesta:**
```
Content-Type: application/pdf
Content-Disposition: attachment; filename=report.pdf
```

**Respuesta exitosa `200 OK`:** Archivo PDF descargable.

---

### 8. ⚙️ Configuración — `/api/settings`

#### GET `/api/settings`
Retorna la configuración pública del sistema (orígenes CORS permitidos).

**Respuesta exitosa `200 OK`:**
```json
{
  "corsAllowedOrigins": [
    "http://localhost:5173",
    "http://127.0.0.1:5173"
  ]
}
```

---

### 9. 📜 Historial — `/api/history`

#### GET `/api/history`
Lista el historial de operaciones del sistema.

**Respuesta exitosa `200 OK`:** Lista de objetos `History`.

---

## 🗂️ Estructura del proyecto

```
techrepair-backend/
├── src/
│   ├── main/
│   │   ├── java/com/techrepair/backend/
│   │   │   ├── controller/       ← Endpoints REST
│   │   │   ├── service/          ← Lógica de negocio
│   │   │   ├── repository/       ← Acceso a base de datos
│   │   │   ├── model/            ← Entidades JPA
│   │   │   ├── dto/              ← Objetos de transferencia
│   │   │   │   ├── request/
│   │   │   │   └── response/
│   │   │   ├── security/         ← JWT y autenticación
│   │   │   ├── enums/            ← Enumeraciones
│   │   │   ├── exception/        ← Manejo de errores
│   │   │   └── config/           ← Configuraciones
│   │   └── resources/
│   │       └── application.properties
│   └── test/                     ← Pruebas unitarias
└── pom.xml
```

---

## 🔒 Seguridad

El sistema usa **JSON Web Tokens (JWT)** con algoritmo **HS512**.

- Los tokens se envían en el header `Authorization: Bearer <token>`
- El filtro `JwtAuthenticationFilter` valida el token en cada petición
- Los roles se manejan con Spring Security: `ROLE_ADMIN`, `ROLE_TECHNICIAN`, `ROLE_CLIENT`

---

## 📦 Repositorio

🔗 https://github.com/Dav1d0494/techrepair-backend
