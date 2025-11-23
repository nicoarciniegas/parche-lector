<template>
  <div class="user-profile-page" v-if="user">
    <header class="profile-header">
      <div class="avatar-large">
        <img :src="user.userAvatar" :alt="user.userName" />
      </div>
      <div class="user-info">
        <h1>{{ user.userName }}</h1>
        <p class="bio">{{ user.bio }}</p>
        
        <div class="stats">
          <div class="stat">
            <span class="num">{{ stats?.followersCount || 0 }}</span>
            <span class="label">Seguidores</span>
          </div>
          <div class="stat">
            <span class="num">{{ stats?.followingCount || 0 }}</span>
            <span class="label">Seguidos</span>
          </div>
        </div>

        <div class="actions" v-if="!isMe">
          <button 
            class="btn-primary" 
            :class="{ 'btn-outline': isFollowing }"
            @click="toggleFollow"
          >
            {{ isFollowing ? 'Siguiendo' : 'Seguir' }}
          </button>
        </div>
      </div>
    </header>

    <div class="content-tabs">
      <button 
        :class="{ active: activeTab === 'lists' }" 
        @click="activeTab = 'lists'"
      >
        Listas
      </button>
      <button 
        :class="{ active: activeTab === 'reviews' }" 
        @click="activeTab = 'reviews'"
      >
        Reseñas
      </button>
    </div>

    <div class="tab-content">
      <!-- Lists Tab -->
      <div v-if="activeTab === 'lists'" class="lists-grid">
        <div v-if="lists?.length === 0" class="empty">No hay listas públicas.</div>
        <div v-for="list in lists" :key="list.id" class="list-card" @click="goToList(list.id)">
          <h3>{{ list.name }}</h3>
          <p>{{ list.bookCount }} libros</p>
        </div>
      </div>

      <!-- Reviews Tab -->
      <div v-if="activeTab === 'reviews'" class="reviews-list">
        <div v-if="reviews?.length === 0" class="empty">No hay reseñas públicas.</div>
        <div v-for="review in reviews" :key="review.id" class="review-card">
          <div class="review-header">
            <span class="book-title">{{ review.bookTitle }}</span>
            <span class="rating">{{ '★'.repeat(Math.round(review.rating)) }}</span>
          </div>
          <h4>{{ review.title }}</h4>
          <p>{{ review.body }}</p>
        </div>
      </div>
    </div>
  </div>
  <div v-else-if="isLoading" class="loading">Cargando perfil...</div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserProfile } from '../composables/useProfile'
import { useUserLists, useUserReviews } from '../composables/useUserContent'
import { useFollowUser, useUnfollowUser, useUserStats, useFollowUserStatus } from '../../social/composables/useFollow'
import { useMyProfile } from '../composables/useProfile'

const route = useRoute()
const router = useRouter()
const userId = Number(route.params.id)

const activeTab = ref('lists')

const { data: user, isLoading } = useUserProfile(userId)
const { data: lists } = useUserLists(userId)
const { data: reviews } = useUserReviews(userId)
const { data: stats } = useUserStats(userId)
const { data: isFollowingData } = useFollowUserStatus(userId)
const { data: myProfile } = useMyProfile()

const { mutate: follow } = useFollowUser()
const { mutate: unfollow } = useUnfollowUser()

const isMe = computed(() => myProfile.value?.id === userId)
const isFollowing = computed(() => !!isFollowingData.value)

const toggleFollow = () => {
  if (isFollowing.value) {
    unfollow(userId)
  } else {
    follow({ userId })
  }
}

const goToList = (id: number) => {
  router.push({ name: 'list-detail', params: { id } })
}
</script>

<style scoped>
.user-profile-page {
  max-width: 800px;
  margin: 0 auto;
  padding: 2rem;
}

.profile-header {
  display: flex;
  gap: 2rem;
  margin-bottom: 2rem;
  align-items: center;
}

.avatar-large {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  overflow: hidden;
  border: 4px solid #f9c846;
}

.avatar-large img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.user-info h1 {
  margin: 0 0 0.5rem;
}

.stats {
  display: flex;
  gap: 2rem;
  margin: 1rem 0;
}

.stat {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.stat .num {
  font-weight: bold;
  font-size: 1.2rem;
}

.content-tabs {
  display: flex;
  gap: 1rem;
  border-bottom: 1px solid #ddd;
  margin-bottom: 2rem;
}

.content-tabs button {
  padding: 1rem;
  background: none;
  border: none;
  border-bottom: 3px solid transparent;
  cursor: pointer;
  font-weight: bold;
  color: #666;
}

.content-tabs button.active {
  color: #2e5266;
  border-bottom-color: #f9c846;
}

.list-card, .review-card {
  background: white;
  padding: 1.5rem;
  border-radius: 8px;
  margin-bottom: 1rem;
  box-shadow: 0 2px 4px rgba(0,0,0,0.05);
}

.list-card {
  cursor: pointer;
}

.review-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 0.5rem;
  color: #666;
}

.book-title {
  font-weight: bold;
  color: #2e5266;
}

.rating {
  color: #f5a623;
}

.btn-primary {
  padding: 0.6rem 1.5rem;
  background: #2e5266;
  color: white;
  border-radius: 20px;
  border: none;
  cursor: pointer;
}

.btn-outline {
  background: white;
  color: #2e5266;
  border: 1px solid #2e5266;
}
</style>
