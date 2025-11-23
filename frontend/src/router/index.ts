// src/router/index.ts
import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'
import { authUtils } from '../utils/auth'

// Layouts
import MainLayout from '../layouts/MainLayout.vue'

// Views
import WelcomeView from '../views/WelcomeView.vue'
import LoginView from '../features/auth/views/LoginView.vue'
import RegisterView from '../features/auth/views/RegisterView.vue'
import ForgotPasswordView from '../features/auth/views/ForgotPasswordView.vue'
import ResetPasswordView from '../features/auth/views/ResetPasswordView.vue'

// Authenticated Views
import FeedView from '../features/social/views/FeedView.vue'
import BooksView from '../features/books/views/BooksView.vue'
import BookDetailView from '../features/books/views/BookDetailView.vue'
import FavoritesView from '../features/books/views/FavoritesView.vue'
import ListsView from '../features/lists/views/ListsView.vue'
import ListDetailView from '../features/lists/views/ListDetailView.vue'
import ProfileView from '../features/profile/views/ProfileView.vue'
import UserProfileView from '../features/profile/views/UserProfileView.vue'

const routes: RouteRecordRaw[] = [
  // Public Routes
  {
    path: '/',
    name: 'welcome',
    component: WelcomeView,
    meta: { publicOnly: true },
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView,
    meta: { publicOnly: true },
  },
  {
    path: '/register',
    name: 'register',
    component: RegisterView,
    meta: { publicOnly: true },
  },
  {
    path: '/forgot-password',
    name: 'forgot-password',
    component: ForgotPasswordView,
    meta: { publicOnly: true },
  },
  {
    path: '/reset-password',
    name: 'reset-password',
    component: ResetPasswordView,
    meta: { publicOnly: true },
  },

  // Authenticated Routes (Wrapped in MainLayout)
  {
    path: '/',
    component: MainLayout,
    meta: { requiresAuth: true },
    children: [
      {
        path: 'home',
        name: 'home',
        component: FeedView,
      },
      {
        path: 'explore',
        name: 'explore',
        component: BooksView,
      },
      {
        path: 'books/:id',
        name: 'book-detail',
        component: BookDetailView,
      },
      {
        path: 'favorites',
        name: 'favorites',
        component: FavoritesView,
      },
      {
        path: 'lists',
        name: 'lists',
        component: ListsView,
      },
      {
        path: 'lists/:id',
        name: 'list-detail',
        component: ListDetailView,
      },
      {
        path: 'profile',
        name: 'profile',
        component: ProfileView,
      },
      {
        path: 'users/:id',
        name: 'user-profile',
        component: UserProfileView,
      },
    ],
  },

  // Catch-all
  { path: '/:pathMatch(.*)*', redirect: '/' },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

// Route guard
router.beforeEach((to, _from, next) => {
  const requiresAuth = to.matched.some((record) => record.meta.requiresAuth)
  const publicOnly = to.matched.some((record) => record.meta.publicOnly)
  const isAuthenticated = authUtils.isAuthenticated()

  if (requiresAuth && !isAuthenticated) {
    next({ name: 'welcome' })
    return
  }

  if (publicOnly && isAuthenticated) {
    next({ name: 'home' })
    return
  }

  next()
})

export default router


