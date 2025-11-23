// Composables for User Content endpoints (lists and reviews)
import { useQuery } from '@tanstack/vue-query'
import { apiClient } from '../../../shared/api/apiClient'
import type { ApiResponse, ReadList, Review } from '../../../shared/types/types'

// ============= Query Keys =============
export const userContentKeys = {
  lists: (userId: number) => ['userLists', userId] as const,
  reviews: (userId: number) => ['userReviews', userId] as const,
}

// ============= Get User Lists =============
export const useUserLists = (userId: number) => {
  return useQuery({
    queryKey: userContentKeys.lists(userId),
    queryFn: async () => {
      const response = await apiClient.get<ApiResponse<ReadList[]>>(
        `/users/${userId}/lists`
      )
      return response.data.data
    },
  })
}

// ============= Get User Reviews =============
export const useUserReviews = (userId: number) => {
  return useQuery({
    queryKey: userContentKeys.reviews(userId),
    queryFn: async () => {
      const response = await apiClient.get<ApiResponse<Review[]>>(
        `/users/${userId}/reviews`
      )
      return response.data.data
    },
  })
}
