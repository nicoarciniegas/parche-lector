<template>
  <div class="auth-page">
    <div class="auth-card">
      <h2>Recuperar Contraseña</h2>
      <p>Ingresa tu email y te enviaremos un enlace para resetear tu contraseña.</p>
      
      <form @submit="onSubmit">
        <div class="form-group">
          <label>Email</label>
          <input v-model="email" type="email" placeholder="tu@email.com" />
          <span class="error" v-if="errors.email">{{ errors.email }}</span>
        </div>
        
        <div v-if="isSuccess" class="success-message">
          ¡Email enviado! Revisa tu bandeja de entrada.
        </div>
        <div v-if="error" class="error-message">
          {{ error.message }}
        </div>

        <button type="submit" class="btn-primary" :disabled="isPending">
          {{ isPending ? 'Enviando...' : 'Enviar enlace' }}
        </button>
      </form>
      
      <div class="auth-links">
        <router-link to="/login">Volver al login</router-link>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useForgotPassword } from '../composables/useForgotPassword'

const { email, errors, onSubmit, isPending, error, isSuccess } = useForgotPassword()
</script>

<style scoped>
.auth-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f5f0e9;
}

.auth-card {
  background: white;
  padding: 2.5rem;
  border-radius: 16px;
  width: 100%;
  max-width: 400px;
  box-shadow: 0 4px 20px rgba(0,0,0,0.1);
}

h2 {
  margin-top: 0;
  color: #2e5266;
}

.form-group {
  margin-bottom: 1.5rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: bold;
}

.form-group input {
  width: 100%;
  padding: 0.8rem;
  border: 1px solid #ddd;
  border-radius: 8px;
}

.error {
  color: red;
  font-size: 0.8rem;
}

.btn-primary {
  width: 100%;
  padding: 1rem;
  background: #2e5266;
  color: white;
  border: none;
  border-radius: 8px;
  font-weight: bold;
  cursor: pointer;
}

.btn-primary:disabled {
  opacity: 0.7;
}

.auth-links {
  margin-top: 1.5rem;
  text-align: center;
}

.success-message {
  background: #e8f5e9;
  color: #2e7d32;
  padding: 1rem;
  border-radius: 8px;
  margin-bottom: 1rem;
}
</style>
