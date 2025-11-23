<template>
  <div class="app-layout">
    <!-- Header fijo -->
    <header class="app-header">
      <div class="header-content">
        <h1 @click="router.push('/home')" class="logo">El Parche Lector</h1>
        
        <div class="header-actions">
          <!-- User Menu -->
          <div class="user-menu-wrapper" @mouseenter="openMenu" @mouseleave="closeMenu">
            <div class="user-avatar" @click="toggleMenu">
              <img :src="userAvatar" alt="Avatar" />
            </div>

            <div v-show="showMenu" class="user-menu">
              <button class="menu-item" @click="goToProfile">
                <span>👤</span> Mi perfil
              </button>
              <button class="menu-item" @click="goToMyLists">
                <span>📋</span> Mis listas
              </button>
              <button class="menu-item" @click="goToFavorites">
                <span>❤️</span> Favoritos
              </button>
              <div class="menu-separator" />
              <button class="menu-item logout" @click="logout">
                <span>🚪</span> Cerrar sesión
              </button>
            </div>
          </div>
        </div>
      </div>
    </header>

    <!-- Sidebar (Desktop) -->
    <aside class="sidebar">
      <nav class="sidebar-nav">
        <router-link to="/home" class="nav-item" active-class="active">
          <span>🏠</span> Inicio
        </router-link>
        <router-link to="/explore" class="nav-item" active-class="active">
          <span>🔍</span> Explorar
        </router-link>
        <router-link to="/lists" class="nav-item" active-class="active">
          <span>📚</span> Listas
        </router-link>
        <router-link to="/profile" class="nav-item" active-class="active">
          <span>👤</span> Perfil
        </router-link>
      </nav>
    </aside>

    <!-- Main Content -->
    <main class="main-content">
      <router-view></router-view>
    </main>

    <!-- Bottom Nav (Mobile) -->
    <nav class="bottom-nav">
      <router-link to="/home" class="nav-item" active-class="active">
        <span>🏠</span>
      </router-link>
      <router-link to="/explore" class="nav-item" active-class="active">
        <span>🔍</span>
      </router-link>
      <router-link to="/lists" class="nav-item" active-class="active">
        <span>📚</span>
      </router-link>
      <router-link to="/profile" class="nav-item" active-class="active">
        <span>👤</span>
      </router-link>
    </nav>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { authUtils } from '../utils/auth'
import { useMyProfile } from '../features/profile/composables/useProfile'

const router = useRouter()
const showMenu = ref(false)
const { data: profile } = useMyProfile()
let closeTimer: ReturnType<typeof setTimeout> | null = null

// Fallback avatar if profile not loaded yet
const userAvatar = ref('https://api.dicebear.com/7.x/avataaars/svg?seed=User&backgroundColor=F9C846')

// Update avatar when profile loads
import { watchEffect } from 'vue'
watchEffect(() => {
  if (profile.value?.userAvatar) {
    userAvatar.value = profile.value.userAvatar
  }
})

const openMenu = () => {
  if (closeTimer) {
    clearTimeout(closeTimer)
    closeTimer = null
  }
  showMenu.value = true
}

const closeMenu = () => {
  closeTimer = setTimeout(() => {
    showMenu.value = false
  }, 200)
}

const toggleMenu = () => {
  if (showMenu.value) {
    closeMenu()
  } else {
    openMenu()
  }
}

const goToProfile = () => router.push('/profile')
const goToMyLists = () => router.push('/lists') // Or filter by my lists
const goToFavorites = () => router.push('/favorites')

const logout = () => {
  authUtils.removeToken()
  router.push('/')
}
</script>

<style scoped>
.app-layout {
  min-height: 100vh;
  background: #f5f0e9;
  padding-top: 70px; /* Header height */
}

/* Header */
.app-header {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  height: 70px;
  background: #f5f0e9;
  border-bottom: 1px solid rgba(46, 82, 102, 0.1);
  z-index: 100;
  display: flex;
  align-items: center;
  padding: 0 1.5rem;
}

.header-content {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.logo {
  font-size: 1.5rem;
  color: #2e5266;
  cursor: pointer;
  margin: 0;
}

.user-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  overflow: hidden;
  cursor: pointer;
  border: 2px solid #f9c846;
}

.user-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.user-menu-wrapper {
  position: relative;
  padding-bottom: 10px; /* Extend wrapper hit area slightly */
  margin-bottom: -10px;
}

.user-menu {
  position: absolute;
  top: 100%;
  right: 0;
  margin-top: 0; /* Removed margin, handled by wrapper padding or top offset */
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0,0,0,0.1);
  padding: 0.5rem;
  min-width: 180px;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

/* Bridge the gap */
.user-menu::before {
  content: '';
  position: absolute;
  top: -20px;
  left: 0;
  right: 0;
  height: 20px;
  background: transparent;
}

.menu-item {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 0.8rem;
  border: none;
  background: transparent;
  cursor: pointer;
  border-radius: 8px;
  transition: background 0.2s;
  text-align: left;
  font-weight: 600;
  color: #2e5266;
}

.menu-item:hover {
  background: #f5f0e9;
}

.menu-item.logout {
  color: #d32f2f;
}

.menu-separator {
  height: 1px;
  background: #eee;
  margin: 0.2rem 0;
}

/* Sidebar */
.sidebar {
  position: fixed;
  left: 0;
  top: 70px;
  bottom: 0;
  width: 240px;
  background: white;
  border-right: 1px solid rgba(46, 82, 102, 0.1);
  padding: 2rem 1rem;
  display: none; /* Hidden on mobile */
}

.sidebar-nav {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.nav-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 1rem;
  text-decoration: none;
  color: #666;
  font-weight: 600;
  border-radius: 12px;
  transition: all 0.2s;
}

.nav-item:hover {
  background: #f5f0e9;
  color: #2e5266;
}

.nav-item.active {
  background: #f9c846;
  color: #2e5266;
}

/* Main Content */
.main-content {
  padding: 2rem;
  max-width: 1200px;
  margin: 0 auto;
}

/* Bottom Nav (Mobile) */
.bottom-nav {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  height: 60px;
  background: white;
  border-top: 1px solid rgba(46, 82, 102, 0.1);
  display: flex;
  justify-content: space-around;
  align-items: center;
  z-index: 100;
}

.bottom-nav .nav-item {
  flex-direction: column;
  gap: 4px;
  font-size: 0.8rem;
  padding: 0.5rem;
}

/* Responsive */
@media (min-width: 768px) {
  .sidebar {
    display: block;
  }
  .bottom-nav {
    display: none;
  }
  .main-content {
    margin-left: 240px; /* Sidebar width */
  }
}
</style>
