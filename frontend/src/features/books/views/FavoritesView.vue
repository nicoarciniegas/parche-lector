<template>
  <div class="favorites-page">
    <header class="page-header">
      <h1>Mis Favoritos</h1>
    </header>

    <div v-if="isLoading" class="loading">Cargando favoritos...</div>
    <div v-else-if="favorites?.length === 0" class="empty-state">
      No tienes libros favoritos aún.
    </div>
    <div v-else class="books-grid">
      <div v-for="book in favorites" :key="book.id" class="book-card" @click="goToBook(book.id)">
        <div class="book-cover">
          <img :src="book.cover || book.coverUrl" :alt="book.title" />
        </div>
        <div class="book-info">
          <h3>{{ book.title }}</h3>
          <p class="author">{{ book.author }}</p>
          <button class="btn-remove" @click.stop="remove(book.id)">💔</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { useFavorites, useRemoveFavorite } from '../composables/useFavorites'

const router = useRouter()
const { data: favorites, isLoading } = useFavorites()
const { mutate: removeFavorite } = useRemoveFavorite()

const remove = (id: number) => {
  if (confirm('¿Quitar de favoritos?')) {
    removeFavorite(id)
  }
}

const goToBook = (id: number) => {
  router.push({ name: 'book-detail', params: { id } })
}
</script>

<style scoped>
.favorites-page {
  padding: 2rem;
  max-width: 1200px;
  margin: 0 auto;
}

.books-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(160px, 1fr));
  gap: 2rem;
}

.book-card {
  background: white;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  cursor: pointer;
  position: relative;
}

.book-cover {
  aspect-ratio: 2/3;
}

.book-cover img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.book-info {
  padding: 1rem;
}

.book-info h3 {
  margin: 0 0 0.5rem;
  font-size: 1rem;
}

.btn-remove {
  position: absolute;
  top: 10px;
  right: 10px;
  background: white;
  border: none;
  border-radius: 50%;
  width: 30px;
  height: 30px;
  cursor: pointer;
  box-shadow: 0 2px 4px rgba(0,0,0,0.2);
}
</style>
