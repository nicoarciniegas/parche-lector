// Composables for Favorites endpoints
import { useQuery, useMutation, useQueryClient } from '@tanstack/vue-query'
import { apiClient } from '../../../shared/api/apiClient'
import type { ApiResponse, Book, FavoriteRequest } from '../../../shared/types/types'

// ============= Query Keys =============
export const favoritesKeys = {
  all: ['favorites'] as const,
  list: () => [...favoritesKeys.all, 'list'] as const,
}

// ============= Get Favorites =============
export const useFavorites = () => {
  return useQuery({
    queryKey: favoritesKeys.list(),
    queryFn: async () => {
      const response = await apiClient.get<ApiResponse<Book[]>>(
        '/books/favorites'
      )
      return response.data.data
    },
  })
}

// ============= Add to Favorites =============
export const useAddFavorite = () => {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: async (data: FavoriteRequest) => {
      const response = await apiClient.post<ApiResponse<null>>(
        '/books/favorites',
        data
      )
      return response.data
    },
    onSuccess: () => {
      // Invalidate favorites to refetch
      queryClient.invalidateQueries({ queryKey: favoritesKeys.all })
    },
  })
}

// ============= Remove from Favorites =============
export const useRemoveFavorite = () => {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: async (bookId: number) => {
      const response = await apiClient.delete<ApiResponse<null>>(
        `/books/favorites/${bookId}`
      )
      return response.data
    },
    onSuccess: () => {
      // Invalidate favorites to refetch
      queryClient.invalidateQueries({ queryKey: favoritesKeys.all })
    },
  })
}
