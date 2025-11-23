<template>
  <div class="book-detail-page" v-if="book">
    <div class="book-hero">
      <div class="book-cover-large">
        <img :src="book.cover || book.coverUrl || ''" :alt="book.title" />
      </div>
      <div class="book-main-info">
        <h1>{{ book.title }}</h1>
        <h2 class="author">by {{ book.author }}</h2>
        
        <div class="book-stats">
          <div class="rating-large">
            <span class="star">★</span>
            <span>{{ (book.rating || 0).toFixed(1) }}</span>
          </div>
          <div class="reviews-count" v-if="reviewsData">
            {{ reviewsData.totalReviews }} reseñas
          </div>
        </div>

        <div class="actions">
          <select v-model="currentStatus" @change="updateStatus" class="status-select">
            <option value="">Sin estado</option>
            <option value="WANT_TO_READ">Por leer</option>
            <option value="READING">Leyendo</option>
            <option value="READ">Leído</option>
          </select>
          
          <button 
            class="btn-favorite" 
            :class="{ active: isFavorite }"
            @click="toggleFavorite"
          >
            {{ isFavorite ? '❤️ En Favoritos' : '🤍 Añadir a Favoritos' }}
          </button>
        </div>
      </div>
    </div>

    <div class="content-split">
      <div class="reviews-section">
        <h3>Reseñas</h3>
        
        <!-- My Review Section -->
        <div class="my-review-card" v-if="myReview">
          <h4>Tu reseña</h4>
          <div class="review-content">
            <div class="review-header">
              <div class="stars">{{ '★'.repeat(Math.round(myReview.rating)) }}</div>
              <span class="date">{{ new Date(myReview.createdAt).toLocaleDateString() }}</span>
            </div>
            <h5>{{ myReview.title }}</h5>
            <p>{{ myReview.body }}</p>
            <button @click="isEditingReview = true" class="btn-text">Editar</button>
          </div>
        </div>
        <div v-else class="add-review-cta">
          <button @click="isCreatingReview = true" class="btn-primary">Escribir reseña</button>
        </div>

        <!-- All Reviews -->
        <div class="reviews-list" v-if="reviewsData?.reviews">
          <div v-for="review in reviewsData.reviews" :key="review.id" class="review-card">
            <div class="review-user">
              <img :src="review.userAvatar" :alt="review.username" class="avatar-small" />
              <span>{{ review.username }}</span>
            </div>
            <div class="review-content">
              <div class="stars">{{ '★'.repeat(Math.round(review.rating)) }}</div>
              <h5>{{ review.title }}</h5>
              <p>{{ review.body }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Modals for Review -->
    <!-- (Simplified for now, would be separate components ideally) -->
  </div>
  <div v-else-if="isLoading" class="loading">Cargando libro...</div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'
import { useBookReviews, useMyReview } from '../../reviews/composables/useReviews'
import { useUpdateReadingStatus } from '../composables/useReadingStatus'
import { useFavorites, useAddFavorite, useRemoveFavorite } from '../composables/useFavorites'
// Note: We need a way to get single book details if not passed via props. 
// The API list didn't explicitly show GET /books/{id}, but usually it exists or we use the data from the list.
// Assuming we might need to fetch it or use what we have. 
// Actually, looking at the API docs again, there isn't a clear GET /books/{id} endpoint listed in the "Books" section.
// However, GET /books/search or trending returns full book objects.
// Let's assume for now we might need to rely on the list data or maybe I missed an endpoint.
// Wait, GET /books/search returns books. 
// If there is no GET /books/{id}, we might have a problem for deep linking.
// Let's check the API_README.md again carefully.
// It lists: GET /books/trending, GET /books/search, GET /books/filter.
// It does NOT list GET /books/{id}. This is odd for a detail view.
// BUT, GET /lists/{id} returns books.
// Maybe we can search by ID? Or maybe it's missing from docs.
// For now, I will assume I can't fetch a single book easily without searching.
// I'll implement a workaround: useSearchBooks with a query for the ID? No, that searches title/author.
// I'll assume for now we pass the book object via state or store, OR I'll implement a "fetch by ID" if it exists but wasn't documented, 
// OR I'll use the reviews endpoint to get some book info?
// GET /reviews/book/{bookId} returns:
// { bookId, bookTitle, averageRating, totalReviews, reviews: [...] }
// It doesn't return the author or cover in the root, but the reviews might have it?
// The reviews array items have: bookTitle, bookCover.
// So I can reconstruct some book info from the reviews endpoint!

const route = useRoute()
const bookId = Number(route.params.id)

const { data: reviewsData, isLoading: loadingReviews } = useBookReviews(bookId)
const { data: myReview } = useMyReview(bookId)
const { mutate: updateReadingStatus } = useUpdateReadingStatus()
const { data: favorites } = useFavorites()
const { mutate: addFavorite } = useAddFavorite()
const { mutate: removeFavorite } = useRemoveFavorite()

const isFavorite = computed(() => {
  return favorites.value?.some(f => f.id === bookId) ?? false
})

const book = computed(() => {
  if (!reviewsData.value) return null
  // Reconstruct book info from reviews response
  // Note: The API response for /reviews/book/{bookId} has bookTitle, averageRating.
  // It might be missing author and cover in the root object based on the docs.
  // Docs say:
  // "data": { "bookId": 1, "bookTitle": "...", "averageRating": 4.7, "totalReviews": 156, "reviews": [...] }
  // Review items have: "bookCover": "..."
  // So we can grab cover from the first review if available, or use a placeholder.
  // Author seems missing from this endpoint.
  // This is a limitation. I might need to rely on the previous view passing data or a store.
  // For now, I'll use what I have.
  
  const cover = reviewsData.value.reviews?.[0]?.bookCover || null
  
  return {
    id: bookId,
    title: reviewsData.value.bookTitle,
    author: 'Unknown Author', // Limitation of current API endpoints
    rating: reviewsData.value.averageRating,
    cover: cover,
    coverUrl: cover
  }
})

const currentStatus = ref('') // This should ideally be populated from the backend

const updateStatus = () => {
  if (currentStatus.value) {
    updateReadingStatus({ bookId, status: currentStatus.value as any })
  }
}

const toggleFavorite = () => {
  if (isFavorite.value) {
    removeFavorite(bookId)
  } else {
    addFavorite({ bookId })
  }
}

const isCreatingReview = ref(false)
const isEditingReview = ref(false)
const isLoading = computed(() => loadingReviews.value)

</script>

<style scoped>
.book-detail-page {
  max-width: 1000px;
  margin: 0 auto;
  padding: 2rem;
}

.book-hero {
  display: flex;
  gap: 2rem;
  margin-bottom: 3rem;
}

.book-cover-large {
  width: 250px;
  flex-shrink: 0;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0,0,0,0.15);
}

.book-cover-large img {
  width: 100%;
  height: auto;
  display: block;
}

.book-main-info h1 {
  font-size: 2.5rem;
  margin: 0 0 0.5rem;
}

.book-main-info .author {
  font-size: 1.2rem;
  color: #666;
  font-weight: normal;
  margin: 0 0 1.5rem;
}

.book-stats {
  display: flex;
  align-items: center;
  gap: 1.5rem;
  margin-bottom: 2rem;
}

.rating-large {
  font-size: 1.5rem;
  font-weight: bold;
  color: #f5a623;
}

.actions {
  display: flex;
  gap: 1rem;
}

.status-select {
  padding: 0.8rem;
  border-radius: 8px;
  border: 1px solid #ddd;
  font-size: 1rem;
}

.btn-favorite {
  padding: 0.8rem 1.5rem;
  border-radius: 8px;
  border: 1px solid #ddd;
  background: white;
  cursor: pointer;
  transition: all 0.2s;
}

.btn-favorite.active {
  background: #fff0f0;
  border-color: #ffcccc;
  color: #d32f2f;
}

.reviews-section {
  background: #f9f9f9;
  padding: 2rem;
  border-radius: 12px;
}

.review-card {
  background: white;
  padding: 1.5rem;
  border-radius: 8px;
  margin-bottom: 1rem;
  box-shadow: 0 2px 4px rgba(0,0,0,0.05);
}

.review-user {
  display: flex;
  align-items: center;
  gap: 0.8rem;
  margin-bottom: 0.8rem;
  font-weight: bold;
}

.avatar-small {
  width: 32px;
  height: 32px;
  border-radius: 50%;
}
</style>
