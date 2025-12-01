<template>
  <div class="review-form-card">
    <h3>{{ isEditing ? 'Editar tu reseña' : 'Escribir una reseña' }}</h3>
    
    <form @submit.prevent="onSubmit" class="review-form">
      <div class="form-group">
        <label>Calificación</label>
        <div class="star-rating">
          <button 
            v-for="star in 5" 
            :key="star" 
            type="button"
            class="star-btn"
            :class="{ active: star <= form.rating }"
            @click="form.rating = star"
            @mouseenter="hoverRating = star"
            @mouseleave="hoverRating = 0"
          >
            ★
          </button>
        </div>
        <span class="rating-text" v-if="form.rating > 0">{{ ratingLabels[form.rating - 1] }}</span>
      </div>

      <div class="form-group">
        <label for="title">Título</label>
        <input 
          id="title"
          v-model="form.title" 
          type="text" 
          placeholder="Resume tu opinión en pocas palabras"
          required
          class="form-input"
        />
      </div>

      <div class="form-group">
        <label for="body">Reseña</label>
        <textarea 
          id="body"
          v-model="form.body" 
          rows="5" 
          placeholder="¿Qué te pareció el libro? ¿Qué te gustó y qué no?"
          class="form-textarea"
        ></textarea>
      </div>

      <div class="form-actions">
        <button type="button" @click="$emit('cancel')" class="btn-secondary">Cancelar</button>
        <button type="submit" class="btn-primary" :disabled="isSubmitting || form.rating === 0">
          {{ isSubmitting ? 'Enviando...' : (isEditing ? 'Actualizar' : 'Publicar') }}
        </button>
      </div>
    </form>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'

const props = defineProps<{
  initialData?: {
    rating: number
    title: string
    body: string
  }
  isSubmitting?: boolean
  isEditing?: boolean
}>()

const emit = defineEmits<{
  (e: 'submit', data: { rating: number; title: string; body: string }): void
  (e: 'cancel'): void
}>()

const form = reactive({
  rating: 0,
  title: '',
  body: ''
})

const hoverRating = ref(0)

const ratingLabels = ['Malo', 'Regular', 'Bueno', 'Muy bueno', 'Excelente']

onMounted(() => {
  if (props.initialData) {
    form.rating = props.initialData.rating
    form.title = props.initialData.title
    form.body = props.initialData.body
  }
})

const onSubmit = () => {
  if (form.rating === 0) return
  emit('submit', { ...form })
}
</script>

<style scoped>
.review-form-card {
  background: white;
  padding: 1.5rem;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0,0,0,0.1);
  margin-bottom: 2rem;
}

.review-form {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.form-group label {
  font-weight: bold;
  color: #2e5266;
}

.star-rating {
  display: flex;
  gap: 0.5rem;
}

.star-btn {
  background: none;
  border: none;
  font-size: 2rem;
  color: #ddd;
  cursor: pointer;
  transition: color 0.2s;
  padding: 0;
  line-height: 1;
}

.star-btn.active, .star-btn:hover {
  color: #f5a623;
}

.rating-text {
  font-size: 0.9rem;
  color: #666;
  margin-top: 0.2rem;
}

.form-input, .form-textarea {
  padding: 0.8rem;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-family: inherit;
  font-size: 1rem;
}

.form-input:focus, .form-textarea:focus {
  outline: none;
  border-color: #2e5266;
  box-shadow: 0 0 0 2px rgba(46, 82, 102, 0.1);
}

.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 1rem;
}

.btn-primary {
  background-color: #2e5266;
  color: white;
  border: none;
  padding: 0.8rem 1.5rem;
  border-radius: 6px;
  font-weight: bold;
  cursor: pointer;
}

.btn-primary:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.btn-secondary {
  background-color: transparent;
  color: #666;
  border: 1px solid #ddd;
  padding: 0.8rem 1.5rem;
  border-radius: 6px;
  font-weight: bold;
  cursor: pointer;
}

.btn-secondary:hover {
  background-color: #f5f5f5;
}
</style>
