# ============================
# Etapa 1: Build de la app Vue
# ============================
FROM node:22-alpine AS build

WORKDIR /app

# Copiamos package.json y package-lock.json (si existe)
COPY package*.json ./

# Instalamos dependencias
RUN npm install

# Copiamos el resto del código
COPY . .

# Construimos la app para producción
RUN npm run build

# ============================
# Etapa 2: Servir con Nginx
# ============================
FROM nginx:alpine

# Copiamos la carpeta dist generada por Vite
COPY --from=build /app/dist /usr/share/nginx/html

# (Opcional) si después tienes un nginx.conf custom para SPA:
# COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
