<template>
  <div class="auth-container">
    <div class="auth-card">
      <h1 class="auth-title">Iniciar sesión</h1>
      <p class="auth-subtitle">¡Qué bueno verte de nuevo!</p>

      <form @submit="onSubmit" class="auth-form">
        <div class="input-group">
          <label>Nombre de usuario o Email</label>
          <input
            v-model="usernameOrEmail"
            type="text"
            name="usernameOrEmail"
            placeholder="tu@email.com o nombre de usuario"
          />
          <span v-if="errors.usernameOrEmail" class="error-message">{{
            errors.usernameOrEmail
          }}</span>
        </div>

        <div class="input-group">
          <label>Contraseña</label>
          <input
            v-model="password"
            type="password"
            name="password"
            placeholder="••••••••"
          />
          <span v-if="errors.password" class="error-message">{{
            errors.password
          }}</span>
        </div>

        <div v-if="loginError" class="error-message server-error">
          {{ loginError.message }}
        </div>

        <div class="forgot-link">
          <router-link to="/forgot-password">¿Olvidaste tu contraseña?</router-link>
        </div>

        <button
          type="submit"
          class="btn-primary full-width"
          :disabled="isPending"
        >
          {{ isPending ? 'Iniciando sesión...' : 'Iniciar sesión' }}
        </button>
      </form>

      <p class="auth-footer">
        ¿Primera vez aquí?
        <router-link to="/register" class="link-highlight">
          Crear cuenta
        </router-link>
      </p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useLogin } from '../composables/useLogin'

const { usernameOrEmail, password, errors, onSubmit, isPending, loginError } =
  useLogin()
</script>

<style scoped>
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
  transition: border 0.2s;
}

.input-group input:focus {
  outline: none;
  border-color: #f9c846;
}

.forgot-link {
  text-align: right;
  margin-top: -0.75rem;
}

.forgot-link a {
  color: #6b9080;
  font-size: 0.95rem;
  text-decoration: none;
}

.forgot-link a:hover {
  text-decoration: underline;
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
