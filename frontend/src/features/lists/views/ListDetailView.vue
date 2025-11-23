<template>
  <div class="list-detail-page" v-if="list">
    <header class="list-header">
      <div class="header-content">
        <h1>{{ list.name }}</h1>
        <p class="description">{{ list.description }}</p>
        <div class="meta">
          <span class="badge">{{ list.visibility }}</span>
          <span class="count">{{ list.bookCount }} libros</span>
        </div>
      </div>
      <div class="header-actions" v-if="isOwner">
        <button class="btn-outline" @click="deleteList">Eliminar Lista</button>
      </div>
    </header>

    <div class="books-list">
      <div v-if="list.books.length === 0" class="empty-state">
        Esta lista está vacía.
      </div>
      <div v-else class="book-item" v-for="book in list.books" :key="book.bookId">
        <div class="book-cover">
          <img :src="book.coverUrl" :alt="book.title" />
        </div>
        <div class="book-info">
          <h3>{{ book.title }}</h3>
          <p class="author">{{ book.authors?.[0] || 'Desconocido' }}</p>
          <p class="note" v-if="book.note">"{{ book.note }}"</p>
        </div>
        <div class="book-actions" v-if="isOwner">
          <button class="btn-icon" @click="removeBook(book.bookId)">🗑️</button>
        </div>
      </div>
    </div>
  </div>
  <div v-else-if="isLoading" class="loading">Cargando lista...</div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useList, useDeleteList } from '../composables/useLists'
import { useRemoveBookFromList } from '../composables/useListBooks'
import { useMyProfile } from '../../profile/composables/useProfile'

const route = useRoute()
const router = useRouter()
const listId = Number(route.params.id)

const { data: list, isLoading } = useList(listId)
const { data: profile } = useMyProfile()
const { mutate: deleteListMutation } = useDeleteList()
const { mutate: removeBookMutation } = useRemoveBookFromList(listId)

const isOwner = computed(() => {
  return list.value?.userId === profile.value?.id
})

const deleteList = () => {
  if (confirm('¿Estás seguro de eliminar esta lista?')) {
    deleteListMutation(listId, {
      onSuccess: () => router.push('/lists')
    })
  }
}

const removeBook = (bookId: number) => {
  if (confirm('¿Quitar libro de la lista?')) {
    removeBookMutation(bookId)
  }
}
</script>

<style scoped>
.list-detail-page {
  max-width: 800px;
  margin: 0 auto;
  padding: 2rem;
}

.list-header {
  background: white;
  padding: 2rem;
  border-radius: 12px;
  margin-bottom: 2rem;
  box-shadow: 0 2px 8px rgba(0,0,0,0.05);
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}

.header-content h1 {
  margin: 0 0 0.5rem;
  color: #2e5266;
}

.description {
  color: #666;
  margin-bottom: 1rem;
}

.meta {
  display: flex;
  gap: 1rem;
}

.badge {
  background: #eee;
  padding: 0.2rem 0.6rem;
  border-radius: 1rem;
  font-size: 0.8rem;
}

.books-list {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.book-item {
  display: flex;
  gap: 1.5rem;
  background: white;
  padding: 1rem;
  border-radius: 8px;
  align-items: center;
}

.book-cover {
  width: 60px;
  height: 90px;
  flex-shrink: 0;
}

.book-cover img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 4px;
}

.book-info {
  flex: 1;
}

.book-info h3 {
  margin: 0 0 0.3rem;
  font-size: 1.1rem;
}

.note {
  font-style: italic;
  color: #666;
  margin-top: 0.5rem;
}

.btn-icon {
  background: none;
  border: none;
  cursor: pointer;
  font-size: 1.2rem;
  padding: 0.5rem;
}

.btn-outline {
  padding: 0.5rem 1rem;
  border: 1px solid #d32f2f;
  color: #d32f2f;
  background: white;
  border-radius: 6px;
  cursor: pointer;
}
</style>
