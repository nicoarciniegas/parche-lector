<template>
  <div class="feed-page">
    <header class="page-header">
      <h1>Tu Feed</h1>
    </header>

    <div class="feed-container">
      <div v-if="status === 'pending'" class="loading">Cargando feed...</div>
      <div v-else-if="status === 'error'" class="error">Error al cargar el feed</div>
      
      <div v-else class="feed-list">
        <!-- Empty State -->
        <div v-if="data?.pages[0]?.items.length === 0" class="empty-state">
          <div class="empty-icon">📭</div>
          <h3>Tu feed está vacío</h3>
          <p>Parece que aún no sigues a nadie o no hay actividad reciente.</p>
          <p>¡Busca amigos o autores para ver sus reseñas y listas aquí!</p>
          <router-link :to="{ name: 'explore' }" class="btn-explore">Explorar Libros</router-link>
        </div>

        <div v-for="page in data?.pages" :key="page.offset">
          <div v-for="item in page.items" :key="getKey(item)" class="feed-item">
            
            <!-- Review Item -->
            <div v-if="item.type === 'REVIEW' && item.review" class="feed-review">
              <div class="feed-header">
                <img :src="item.userAvatar" class="avatar" />
                <div class="meta">
                  <span class="username">{{ item.username }}</span>
                  <span class="action">reseñó</span>
                  <span class="book-title">{{ item.review.bookTitle }}</span>
                  <span class="date">{{ formatDate(item.createdAt) }}</span>
                </div>
              </div>
              
              <div class="review-body">
                <div class="rating">
                  {{ '★'.repeat(Math.round(item.review.rating)) }}
                </div>
                <h4>{{ item.review.title }}</h4>
                <p>{{ item.review.body }}</p>
                
                <div class="review-book-preview">
                  <img :src="item.review.bookCover" class="book-cover-small" />
                </div>
              </div>
            </div>

            <!-- List Item -->
            <div v-if="item.type === 'LIST' && item.list" class="feed-list-item">
              <div class="feed-header">
                <img :src="item.userAvatar" class="avatar" />
                <div class="meta">
                  <span class="username">{{ item.username }}</span>
                  <span class="action">creó una lista</span>
                  <span class="list-name">{{ item.list.name }}</span>
                  <span class="date">{{ formatDate(item.createdAt) }}</span>
                </div>
              </div>
              
              <div class="list-body">
                <p>{{ item.list.description }}</p>
                <div class="list-stats">
                  <span>{{ item.list.bookCount }} libros</span>
                </div>
              </div>
            </div>

          </div>
        </div>
        
        <div ref="loadMoreTrigger" class="load-more-trigger">
          <span v-if="isFetchingNextPage">Cargando más...</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { useFeed } from '../composables/useFeed'
import type { FeedItem } from '../../../shared/types/types'

const { 
  data, 
  fetchNextPage, 
  hasNextPage, 
  isFetchingNextPage,
  status 
} = useFeed(10)

const loadMoreTrigger = ref<HTMLElement | null>(null)

// Simple infinite scroll observer
let observer: IntersectionObserver | null = null

onMounted(() => {
  observer = new IntersectionObserver((entries) => {
    if (entries[0].isIntersecting && hasNextPage.value) {
      fetchNextPage()
    }
  })
  
  if (loadMoreTrigger.value) {
    observer.observe(loadMoreTrigger.value)
  }
})

onUnmounted(() => {
  if (observer) observer.disconnect()
})

const getKey = (item: FeedItem) => {
  return `${item.type}-${item.createdAt}-${item.userId}`
}

const formatDate = (dateStr: string) => {
  return new Date(dateStr).toLocaleDateString()
}
</script>

<style scoped>
.feed-page {
  max-width: 800px;
  margin: 0 auto;
  padding: 2rem;
}

.feed-item {
  background: white;
  border-radius: 12px;
  padding: 1.5rem;
  margin-bottom: 1.5rem;
  box-shadow: 0 2px 8px rgba(0,0,0,0.05);
}

.feed-header {
  display: flex;
  align-items: center;
  gap: 1rem;
  margin-bottom: 1rem;
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}

.meta {
  display: flex;
  flex-direction: column;
  font-size: 0.9rem;
}

.username {
  font-weight: bold;
  color: #2e5266;
}

.action {
  color: #666;
  margin: 0 4px;
}

.book-title, .list-name {
  font-weight: bold;
  color: #d32f2f; /* Accent color */
}

.date {
  font-size: 0.8rem;
  color: #999;
}

.review-body, .list-body {
  padding-left: 3.5rem; /* Align with text start */
}

.rating {
  color: #f5a623;
  margin-bottom: 0.5rem;
}

.book-cover-small {
  width: 60px;
  border-radius: 4px;
  margin-top: 1rem;
}

.load-more-trigger {
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #666;
}

.empty-state {
  text-align: center;
  padding: 3rem;
  background: white;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.05);
  color: #666;
}

.empty-icon {
  font-size: 3rem;
  margin-bottom: 1rem;
}

.btn-explore {
  display: inline-block;
  margin-top: 1.5rem;
  padding: 0.75rem 1.5rem;
  background-color: #2e5266;
  color: white;
  text-decoration: none;
  border-radius: 6px;
  font-weight: bold;
  transition: background-color 0.2s;
}

.btn-explore:hover {
  background-color: #1a3a4a;
}
</style>
