# Deployment Guide

This guide describes how to deploy the Parche Lector application in a production environment using Docker and Nginx.

## Prerequisites

- **Docker** (v20.10+)
- **Docker Compose** (v2.0+)
- A server with at least 2GB RAM and 10GB disk space.
- A domain name (optional but recommended).

## Architecture

The deployment consists of three main containers:
1. **Frontend**: Nginx serving the Vue.js static files.
2. **Backend**: Spring Boot application running on OpenJDK.
3. **Database**: PostgreSQL 14.

## Deployment Steps

### 1. Clone the Repository

```bash
git clone https://github.com/nicoarciniegas/parche-lector.git
cd parche-lector
```

### 2. Configure Environment Variables

Create a `.env` file in the root directory based on `.env.example`:

```bash
cp .env.example .env
```

Edit the `.env` file with your production secrets:

```ini
# Database Configuration
DB_PASSWORD=your_secure_db_password
DB_USER=postgres
DB_NAME=parche_lector

# JWT Configuration
JWT_SECRET=your_very_long_and_secure_jwt_secret_key_minimum_64_chars

# Application Configuration
SPRING_PROFILES_ACTIVE=prod
```

### 3. Build and Run with Docker Compose

For production, use the `docker-compose.yml` file. You might want to override some settings for production (e.g., restart policies).

```bash
# Build images and start containers in detached mode
docker-compose up --build -d
```

### 4. Verify Deployment

Check the status of your containers:

```bash
docker-compose ps
```

View logs to ensure everything started correctly:

```bash
docker-compose logs -f
```

### 5. Nginx Configuration (Optional)

The frontend container uses an internal Nginx. For a production server, you likely want a reverse proxy in front of everything to handle SSL/TLS (HTTPS).

Example Nginx configuration for the host machine:

```nginx
server {
    listen 80;
    server_name example.com;

    location / {
        proxy_pass http://localhost:3000;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }

    location /api {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

## Backup and Recovery

### Database Backup

To backup the PostgreSQL database:

```bash
docker-compose exec db pg_dump -U postgres parche_lector > backup_$(date +%Y%m%d).sql
```

### Database Restore

To restore from a backup:

```bash
cat backup_file.sql | docker-compose exec -T db psql -U postgres parche_lector
```

## Troubleshooting

- **Database Connection Failed**: Ensure the `DB_PASSWORD` in `.env` matches what the application expects.
- **Frontend 404s**: Check Nginx configuration in `frontend/nginx.conf`.
- **CORS Errors**: Ensure the backend allows the frontend domain in `CorsConfig.java`.
