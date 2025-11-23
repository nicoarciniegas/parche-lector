<template>
  <div class="books-page">
    <header class="page-header">
      <h1>Explorar Libros</h1>
      <div class="search-bar">
        <input
          v-model="searchQuery"
          type="text"
          placeholder="Buscar por título o autor..."
          @input="handleSearch"
        />
      </div>
    </header>

    <div class="filters-section">
      <select v-model="filters.sortBy">
        <option value="popular">Más Populares</option>
        <option value="rating">Mejor Calificados</option>
        <option value="newest">Más Recientes</option>
        <option value="oldest">Más Antiguos</option>
      </select>
      
      <select v-model="filters.genre">
        <option value="">Todos los géneros</option>
        <option value="Ficción">Ficción</option>
        <option value="No Ficción">No Ficción</option>
        <option value="Fantasía">Fantasía</option>
        <option value="Ciencia Ficción">Ciencia Ficción</option>
        <option value="Misterio">Misterio</option>
        <option value="Romance">Romance</option>
      </select>
    </div>

    <div v-if="isLoading" class="loading">Cargando libros...</div>
    <div v-else-if="error" class="error">Error al cargar libros</div>
    
    <div v-else class="books-grid">
      <div v-for="book in displayBooks" :key="book.id" class="book-card" @click="goToBook(book.id)">
        <div class="book-cover">
          <img :src="book.cover || book.coverUrl" :alt="book.title" />
        </div>
        <div class="book-info">
          <h3>{{ book.title }}</h3>
          <p class="author">{{ book.author }}</p>
          <div class="rating">
            <span class="star">★</span>
            <span>{{ (book.rating || book.averageRating || 0).toFixed(1) }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useTrendingBooks, useSearchBooks, useFilterBooks } from '../composables/useBooks'
import type { BookFilterParams } from '../../../shared/types/types'

const router = useRouter()
const searchQuery = ref('')
const filters = ref<BookFilterParams>({
  sortBy: 'popular',
  genre: '',
})

// We'll switch between queries based on user interaction
const isSearching = computed(() => searchQuery.value.length > 0)
const isFiltering = computed(() => filters.value.genre !== '' || filters.value.sortBy !== 'popular')

// Queries
const { data: trendingBooks, isLoading: loadingTrending } = useTrendingBooks(20)
const { data: searchResults, isLoading: loadingSearch } = useSearchBooks(searchQuery)
const { data: filteredBooks, isLoading: loadingFilter } = useFilterBooks(filters)

const displayBooks = computed(() => {
  if (isSearching.value) return searchResults.value || []
  if (isFiltering.value) return filteredBooks.value || []
  return trendingBooks.value || []
})

const isLoading = computed(() => {
  if (isSearching.value) return loadingSearch.value
  if (isFiltering.value) return loadingFilter.value
  return loadingTrending.value
})

const error = computed(() => {
  // Simplified error handling
  return false
})

const handleSearch = () => {
  // The composable handles reactivity, but we might want to debounce the input update if not handled there
  // For now relying on the composable's reactivity
}

const goToBook = (id: number) => {
  router.push({ name: 'book-detail', params: { id } })
}
</script>

<style scoped>
.books-page {
  padding: 2rem;
  max-width: 1200px;
  margin: 0 auto;
}

.page-header {
  margin-bottom: 2rem;
  text-align: center;
}

.search-bar input {
  width: 100%;
  max-width: 500px;
  padding: 1rem;
  border-radius: 2rem;
  border: 1px solid #ddd;
  font-size: 1.1rem;
  margin-top: 1rem;
}

.filters-section {
  display: flex;
  gap: 1rem;
  justify-content: center;
  margin-bottom: 2rem;
}

.filters-section select {
  padding: 0.5rem 1rem;
  border-radius: 0.5rem;
  border: 1px solid #ddd;
}

.books-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
  gap: 2rem;
}

.book-card {
  cursor: pointer;
  transition: transform 0.2s;
  background: white;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

.book-card:hover {
  transform: translateY(-5px);
}

.book-cover {
  aspect-ratio: 2/3;
  background: #eee;
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
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.author {
  color: #666;
  font-size: 0.9rem;
  margin: 0 0 0.5rem;
}

.rating {
  color: #f5a623;
  font-weight: bold;
}
</style>
