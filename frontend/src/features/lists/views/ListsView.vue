<template>
  <div class="lists-page">
    <header class="page-header">
      <h1>Listas de Lectura</h1>
      <button class="btn-primary" @click="showCreateModal = true">
        + Nueva Lista
      </button>
    </header>

    <!-- Create List Modal -->
    <div v-if="showCreateModal" class="modal-overlay" @click.self="showCreateModal = false">
      <div class="modal">
        <h2>Crear Nueva Lista</h2>
        <form @submit.prevent="handleCreateList">
          <div class="form-group">
            <label>Nombre</label>
            <input v-model="newList.name" required placeholder="Ej: Favoritos de Sci-Fi" />
          </div>
          <div class="form-group">
            <label>Descripción</label>
            <textarea v-model="newList.description" placeholder="¿De qué trata esta lista?"></textarea>
          </div>
          <div class="form-group">
            <label>Visibilidad</label>
            <select v-model="newList.visibility">
              <option value="PUBLIC">Pública</option>
              <option value="PRIVATE">Privada</option>
              <option value="FOLLOWERS_ONLY">Solo Seguidores</option>
            </select>
          </div>
          <div class="modal-actions">
            <button type="button" @click="showCreateModal = false">Cancelar</button>
            <button type="submit" class="btn-primary" :disabled="isCreating">Crear</button>
          </div>
        </form>
      </div>
    </div>

    <!-- Lists Grid -->
    <div class="lists-grid">
      <!-- We need a way to get ALL lists or discovered lists. 
           The API only has GET /lists/{id} and GET /users/{userId}/lists.
           There is no "Get All Public Lists" endpoint in the docs provided.
           However, /social/feed returns lists from followed users.
           For this view, maybe we show the current user's lists?
           Or maybe we need to implement a "Discover Lists" endpoint?
           For now, I'll show the current user's lists using useUserLists with my own ID.
      -->
      <div v-if="isLoading" class="loading">Cargando listas...</div>
      <div v-else-if="myLists?.length === 0" class="empty-state">
        No tienes listas aún. ¡Crea una!
      </div>
      <div v-else class="list-card" v-for="list in myLists" :key="list.id" @click="goToList(list.id)">
        <div class="list-preview">
          <!-- Show covers of first 3 books if available -->
          <div class="mini-covers">
            <img 
              v-for="book in list.books.slice(0, 3)" 
              :key="book.bookId" 
              :src="book.coverUrl" 
              class="mini-cover"
            />
          </div>
        </div>
        <div class="list-info">
          <h3>{{ list.name }}</h3>
          <p>{{ list.bookCount }} libros</p>
          <span class="visibility-badge">{{ list.visibility }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useCreateList } from '../composables/useLists'
import { useUserLists } from '../../profile/composables/useUserContent'
import { useMyProfile } from '../../profile/composables/useProfile'
import type { CreateListRequest } from '../../../shared/types/types'

const router = useRouter()
const showCreateModal = ref(false)
const newList = ref<CreateListRequest>({
  name: '',
  description: '',
  visibility: 'PUBLIC'
})

const { data: profile } = useMyProfile()
// Fetch my lists once we have the profile ID
const { data: myLists, isLoading } = useUserLists(profile.value?.id || 0) // This might need a watcher or computed if profile loads later

const { mutate: createList, isPending: isCreating } = useCreateList()

const handleCreateList = () => {
  createList(newList.value, {
    onSuccess: () => {
      showCreateModal.value = false
      newList.value = { name: '', description: '', visibility: 'PUBLIC' }
    }
  })
}

const goToList = (id: number) => {
  router.push({ name: 'list-detail', params: { id } })
}
</script>

<style scoped>
.lists-page {
  padding: 2rem;
  max-width: 1200px;
  margin: 0 auto;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
}

.lists-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 2rem;
}

.list-card {
  background: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0,0,0,0.08);
  cursor: pointer;
  transition: transform 0.2s;
}

.list-card:hover {
  transform: translateY(-4px);
}

.list-preview {
  height: 150px;
  background: #f0f0f0;
  padding: 1rem;
  display: flex;
  align-items: center;
  justify-content: center;
}

.mini-covers {
  display: flex;
  gap: 0.5rem;
}

.mini-cover {
  width: 60px;
  height: 90px;
  object-fit: cover;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.2);
}

.list-info {
  padding: 1.5rem;
}

.list-info h3 {
  margin: 0 0 0.5rem;
  font-size: 1.2rem;
}

.visibility-badge {
  font-size: 0.8rem;
  padding: 0.2rem 0.6rem;
  background: #eee;
  border-radius: 1rem;
  color: #666;
}

/* Modal Styles */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0,0,0,0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 100;
}

.modal {
  background: white;
  padding: 2rem;
  border-radius: 12px;
  width: 100%;
  max-width: 500px;
}

.form-group {
  margin-bottom: 1rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: bold;
}

.form-group input, .form-group textarea, .form-group select {
  width: 100%;
  padding: 0.8rem;
  border: 1px solid #ddd;
  border-radius: 8px;
}

.modal-actions {
  display: flex;
  justify-content: flex-end;
  gap: 1rem;
  margin-top: 2rem;
}
</style>
