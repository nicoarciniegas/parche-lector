# Parche-Lector 📚

## 🧩 Descripción del Proyecto
Este proyecto forma parte del curso Ingeniería de Software 2 y tiene como objetivo desarrollar una plataforma web que facilite el seguimiento y la organización de la lectura personal. Permite registrar los libros leídos, aquellos que el usuario desea leer en el futuro y los que más le gustan. Además, busca fomentar la interacción entre lectores, ofreciendo un espacio para compartir opiniones, descubrir nuevas obras y conectar con personas que tengan gustos literarios similares.

---

## 👥 Integrantes del Equipo

- Nicolas Arciniegas
- Juan Jose Alvarez Lozano
- Julian Dario Colmenares Saenz
- Julian Santiago Becerra Pulido
- Sebastian Castañeda Garcia

---

## 🚀 Inicio Rápido con Docker

La forma más sencilla de ejecutar el proyecto completo es usando Docker Compose.

### Requisitos Previos

- [Docker](https://docs.docker.com/get-docker/) (v20.10+)
- [Docker Compose](https://docs.docker.com/compose/install/) (v2.0+)

### Levantar el Proyecto

```bash
# Clonar el repositorio
git clone <repository-url>
cd Parche-Lector

# Iniciar todos los servicios
docker-compose up --build -d
```

### Servicios Disponibles

| Servicio | URL | Descripción |
|----------|-----|-------------|
| **Frontend** | http://localhost:3000 | Aplicación Vue.js |
| **Backend API** | http://localhost:8080 | API REST Spring Boot |
| **Swagger UI** | http://localhost:8080/swagger-ui.html | Documentación de la API |
| **PostgreSQL** | localhost:5432 | Base de datos |

### Comandos Docker Útiles

```bash
# Iniciar servicios en segundo plano
docker-compose up -d

# Iniciar servicios y reconstruir imágenes
docker-compose up --build -d

# Ver logs de todos los servicios
docker-compose logs -f

# Ver logs de un servicio específico
docker-compose logs -f backend
docker-compose logs -f frontend
docker-compose logs -f db

# Detener todos los servicios
docker-compose down

# Detener y eliminar volúmenes (reiniciar base de datos)
docker-compose down -v

# Ver estado de los contenedores
docker-compose ps
```

### Variables de Entorno

Puedes personalizar la configuración copiando `.env.example` a `.env`:

```bash
cp .env.example .env
```

Variables disponibles:
- `DB_PASSWORD`: Contraseña de PostgreSQL (default: `123`)
- `JWT_SECRET`: Secreto para tokens JWT

---

## 🛠️ Desarrollo Local (Sin Docker)

### Backend (Spring Boot)

**Requisitos:**
- Java 17+
- Maven 3.9+
- PostgreSQL 14+

```bash
cd backend

# Configurar base de datos local
# Crear base de datos 'parche_lector' en PostgreSQL
# Ejecutar el schema: psql -d parche_lector -f ESQUEMA_PARCHE_LECTOR_POSTGRES.sql
# (Opcional) Cargar datos de prueba: psql -d parche_lector -f DATOS_PRUEBA.sql

# Ejecutar la aplicación
./mvnw spring-boot:run

# O compilar y ejecutar
./mvnw clean package -DskipTests
java -jar target/*.jar
```

El backend estará disponible en http://localhost:8080

### Frontend (Vue.js)

**Requisitos:**
- Node.js 18+
- npm 9+

```bash
cd frontend

# Instalar dependencias
npm install

# Iniciar servidor de desarrollo
npm run dev

# Compilar para producción
npm run build
```

El frontend estará disponible en http://localhost:5173

---

## 🧪 Ejecución de Tests

### Tests del Frontend

```bash
cd frontend

# Ejecutar tests en modo watch
npm run test

# Ejecutar tests una sola vez
npm run test:run

# Ejecutar tests con cobertura
npm run test:coverage
```

### Tests del Backend

```bash
cd backend

# Ejecutar todos los tests
./mvnw test

# Ejecutar tests con reporte detallado
./mvnw test -Dmaven.test.failure.ignore=false

# Ejecutar un test específico
./mvnw test -Dtest=NombreDelTest

# Saltar tests durante la compilación
./mvnw clean package -DskipTests
```

### Type Checking (Frontend)

```bash
cd frontend

# Verificar tipos TypeScript
npm run type-check
```

---

## 🗂️ Estructura del Proyecto

```
Parche-Lector/
├── backend/                    # API REST Spring Boot
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/          # Código fuente Java
│   │   │   └── resources/     # Configuraciones
│   │   └── test/              # Tests unitarios
│   ├── Dockerfile             # Imagen Docker del backend
│   ├── pom.xml                # Dependencias Maven
│   ├── ESQUEMA_PARCHE_LECTOR_POSTGRES.sql  # Schema de BD
│   └── DATOS_PRUEBA.sql       # Datos de prueba
│
├── frontend/                   # Aplicación Vue.js
│   ├── src/
│   │   ├── features/          # Módulos por funcionalidad
│   │   ├── shared/            # Componentes compartidos
│   │   ├── layouts/           # Layouts de la app
│   │   └── router/            # Configuración de rutas
│   ├── Dockerfile             # Imagen Docker del frontend
│   ├── nginx.conf             # Configuración de nginx
│   └── package.json           # Dependencias npm
│
├── docker-compose.yml          # Orquestación de servicios
├── .env.example               # Variables de entorno ejemplo
│
├── workshop-1/                # Primera entrega del curso
├── workshop-2/                # Segunda entrega del curso
└── workshop-3/                # Tercera entrega del curso
    ├── backend/               # Documentación del backend
    └── frontend/              # Documentación del frontend
```

---

## 📡 API Endpoints Principales

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/auth/register` | Registrar usuario |
| POST | `/auth/login` | Iniciar sesión |
| GET | `/auth/me` | Obtener usuario actual |
| GET | `/api/books` | Listar libros |
| GET | `/api/books/{id}` | Obtener libro por ID |
| POST | `/api/reviews` | Crear reseña |
| GET | `/api/lists` | Obtener listas del usuario |
| POST | `/api/lists` | Crear nueva lista |

Para la documentación completa de la API, visita http://localhost:8080/swagger-ui.html

---

## 🔧 Tecnologías Utilizadas

### Backend
- Java 17
- Spring Boot 3.2
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL
- Swagger/OpenAPI

### Frontend
- Vue.js 3
- TypeScript
- Vite
- Vue Router
- TanStack Query (Vue Query)
- Axios

### DevOps
- Docker & Docker Compose
- Nginx (servidor de producción)

---

## 📝 Licencia

Este proyecto es parte del curso de Ingeniería de Software 2.
