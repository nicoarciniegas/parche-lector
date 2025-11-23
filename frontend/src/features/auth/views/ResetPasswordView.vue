<template>
  <div class="auth-page">
    <div class="auth-card">
      <h2>Nueva Contraseña</h2>
      
      <form @submit="onSubmit">
        <div class="form-group">
          <label>Nueva Contraseña</label>
          <input v-model="newPassword" type="password" />
          <span class="error" v-if="errors.newPassword">{{ errors.newPassword }}</span>
        </div>
        
        <div class="form-group">
          <label>Confirmar Contraseña</label>
          <input v-model="confirmPassword" type="password" />
          <span class="error" v-if="errors.confirmPassword">{{ errors.confirmPassword }}</span>
        </div>

        <div v-if="error" class="error-message">
          {{ error.message }}
        </div>

        <button type="submit" class="btn-primary" :disabled="isPending">
          {{ isPending ? 'Restableciendo...' : 'Restablecer Contraseña' }}
        </button>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRoute } from 'vue-router'
import { useResetPassword } from '../composables/useResetPassword'

const route = useRoute()
const token = route.query.token as string

const { 
  newPassword, 
  confirmPassword, 
  errors, 
  onSubmit, 
  isPending, 
  error 
} = useResetPassword(token || '')
</script>

<style scoped>
/* Same styles as ForgotPasswordView */
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
</style>
