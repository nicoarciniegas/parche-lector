<template>
  <div class="auth-container">
    <div class="auth-card">
      <h1 class="auth-title">Crear cuenta</h1>
      <p class="auth-subtitle">Únete a la comunidad lectora</p>

      <form @submit="onSubmit" class="auth-form">
        <div class="input-group">
          <label>Nombre de usuario</label>
          <input
            v-model="username"
            type="text"
            name="username"
            placeholder="sebastian.castaneda"
          />
          <span v-if="errors.username" class="error-message">{{
            errors.username
          }}</span>
        </div>

        <div class="input-group">
          <label>Email</label>
          <input
            v-model="email"
            type="email"
            name="email"
            placeholder="ana@email.com"
          />
          <span v-if="errors.email" class="error-message">{{
            errors.email
          }}</span>
        </div>

        <div class="input-group">
          <label>Contraseña</label>
          <input
            v-model="password"
            type="password"
            name="password"
            placeholder="Mínimo 6 caracteres"
          />
          <span v-if="errors.password" class="error-message">{{
            errors.password
          }}</span>
        </div>

        <div v-if="registerError" class="error-message server-error">
          {{ registerError.message }}
        </div>

        <button
          type="submit"
          class="btn-primary full-width"
          :disabled="isPending"
        >
          {{ isPending ? 'Creando cuenta...' : 'Crear cuenta' }}
        </button>
      </form>

      <p class="auth-footer">
        ¿Ya tienes cuenta?
        <router-link to="/login" class="link-highlight"
          >Iniciar sesión</router-link
        >
      </p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRegister } from '../composables/useRegister'

const {
  username,
  email,
  password,
  errors,
  onSubmit,
  isPending,
  registerError,
} = useRegister()
</script>

<style scoped>
/* Mismo estilo que LoginView – reutiliza todo */
.auth-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 1rem;
}
.auth-card {
  background: #fdfbf7;
  padding: 3rem 2.5rem;
  border-radius: 20px;
  box-shadow: 0 12px 40px rgba(139, 111, 71, 0.15);
  width: 100%;
  max-width: 420px;
}
.auth-title {
  font-size: 2.2rem;
  text-align: center;
  margin-bottom: 0.5rem;
}
.auth-subtitle {
  text-align: center;
  margin-bottom: 2.5rem;
  color: #6b9080;
}
.auth-form {
  display: flex;
  flex-direction: column;
  gap: 1.25rem;
}
.input-group label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: 600;
  color: #2e5266;
}
.input-group input {
  width: 100%;
  padding: 1rem;
  border: 2px solid #e8e2d9;
  border-radius: 12px;
  font-size: 1rem;
}
.input-group input:focus {
  outline: none;
  border-color: #f9c846;
}
.full-width {
  width: 100%;
  margin-top: 1rem;
}
.auth-footer {
  text-align: center;
  margin-top: 2rem;
  font-size: 0.95rem;
}
.link-highlight {
  color: #f9c846;
  font-weight: 600;
  text-decoration: none;
}
.link-highlight:hover {
  text-decoration: underline;
}
.error-message {
  color: #d32f2f;
  font-size: 0.875rem;
  margin-top: 0.25rem;
  display: block;
}
.server-error {
  text-align: center;
  padding: 0.75rem;
  background-color: #ffebee;
  border-radius: 8px;
  margin-bottom: 1rem;
}
.btn-primary:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}
</style>
