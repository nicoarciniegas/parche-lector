// Composables for Reviews endpoints
import { useQuery, useMutation, useQueryClient } from '@tanstack/vue-query'
import { apiClient } from '../../../shared/api/apiClient'
import type {
  ApiResponse,
  Review,
  BookReviewsResponse,
  CreateReviewRequest,
  UpdateReviewRequest,
} from '../../../shared/types/types'

// ============= Query Keys =============
export const reviewsKeys = {
  all: ['reviews'] as const,
  book: (bookId: number) => [...reviewsKeys.all, 'book', bookId] as const,
  myReview: (bookId: number) =>
    [...reviewsKeys.all, 'myReview', bookId] as const,
}

// ============= Get Book Reviews =============
export const useBookReviews = (bookId: number) => {
  return useQuery({
    queryKey: reviewsKeys.book(bookId),
    queryFn: async () => {
      const response = await apiClient.get<ApiResponse<BookReviewsResponse>>(
        `/reviews/book/${bookId}`
      )
      return response.data.data
    },
  })
}

// ============= Get My Review for a Book =============
export const useMyReview = (bookId: number) => {
  return useQuery({
    queryKey: reviewsKeys.myReview(bookId),
    queryFn: async () => {
      const response = await apiClient.get<ApiResponse<Review>>(
        `/reviews/book/${bookId}/my-review`
      )
      return response.data.data
    },
    retry: false, // Don't retry if review doesn't exist
  })
}

// ============= Create Review =============
export const useCreateReview = () => {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: async (data: CreateReviewRequest) => {
      const response = await apiClient.post<ApiResponse<Review>>(
        '/reviews',
        data
      )
      return response.data.data
    },
    onSuccess: (data) => {
      // Invalidate book reviews and my review
      queryClient.invalidateQueries({
        queryKey: reviewsKeys.book(data.bookId),
      })
      queryClient.invalidateQueries({
        queryKey: reviewsKeys.myReview(data.bookId),
      })
      queryClient.invalidateQueries({ queryKey: ['profile'] })
      queryClient.invalidateQueries({ queryKey: ['social', 'feed'] })
    },
  })
}

// ============= Update Review =============
export const useUpdateReview = (reviewId: number) => {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: async (data: UpdateReviewRequest) => {
      const response = await apiClient.put<ApiResponse<Review>>(
        `/reviews/${reviewId}`,
        data
      )
      return response.data.data
    },
    onSuccess: (data) => {
      // Invalidate book reviews and my review
      queryClient.invalidateQueries({
        queryKey: reviewsKeys.book(data.bookId),
      })
      queryClient.invalidateQueries({
        queryKey: reviewsKeys.myReview(data.bookId),
      })
      queryClient.invalidateQueries({ queryKey: ['profile'] })
    },
  })
}

// ============= Delete Review =============
export const useDeleteReview = () => {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: async (reviewId: number) => {
      const response = await apiClient.delete<ApiResponse<null>>(
        `/reviews/${reviewId}`
      )
      return response.data
    },
    onSuccess: () => {
      // Invalidate all reviews queries
      queryClient.invalidateQueries({ queryKey: reviewsKeys.all })
      queryClient.invalidateQueries({ queryKey: ['profile'] })
    },
  })
}
