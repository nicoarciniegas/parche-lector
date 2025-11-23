// Composables for Review Interactions (likes, comments)
import { useQuery, useMutation, useQueryClient } from '@tanstack/vue-query'
import { apiClient } from '../../../shared/api/apiClient'
import type {
  ApiResponse,
  Comment,
  AddCommentRequest,
} from '../../../shared/types/types'
import { reviewsKeys } from './useReviews'

// ============= Query Keys =============
export const reviewInteractionsKeys = {
  likeStatus: (reviewId: number) => ['reviewLike', reviewId] as const,
  comments: (reviewId: number) => ['reviewComments', reviewId] as const,
}

// ============= Like Review =============
export const useLikeReview = () => {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: async (reviewId: number) => {
      const response = await apiClient.post<ApiResponse<null>>(
        `/reviews/${reviewId}/likes`
      )
      return response.data
    },
    onSuccess: (_data, reviewId) => {
      // Invalidate like status and reviews
      queryClient.invalidateQueries({
        queryKey: reviewInteractionsKeys.likeStatus(reviewId),
      })
      queryClient.invalidateQueries({ queryKey: reviewsKeys.all })
    },
  })
}

// ============= Unlike Review =============
export const useUnlikeReview = () => {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: async (reviewId: number) => {
      const response = await apiClient.delete<ApiResponse<null>>(
        `/reviews/${reviewId}/likes`
      )
      return response.data
    },
    onSuccess: (_data, reviewId) => {
      // Invalidate like status and reviews
      queryClient.invalidateQueries({
        queryKey: reviewInteractionsKeys.likeStatus(reviewId),
      })
      queryClient.invalidateQueries({ queryKey: reviewsKeys.all })
    },
  })
}

// ============= Get Like Status =============
export const useLikeStatus = (reviewId: number) => {
  return useQuery({
    queryKey: reviewInteractionsKeys.likeStatus(reviewId),
    queryFn: async () => {
      const response = await apiClient.get<ApiResponse<boolean>>(
        `/reviews/${reviewId}/likes/status`
      )
      return response.data.data
    },
  })
}

// ============= Get Review Comments =============
export const useReviewComments = (reviewId: number) => {
  return useQuery({
    queryKey: reviewInteractionsKeys.comments(reviewId),
    queryFn: async () => {
      const response = await apiClient.get<ApiResponse<Comment[]>>(
        `/reviews/${reviewId}/comments`
      )
      return response.data.data
    },
  })
}

// ============= Add Comment =============
export const useAddComment = (reviewId: number) => {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: async (data: AddCommentRequest) => {
      const response = await apiClient.post<ApiResponse<Comment>>(
        `/reviews/${reviewId}/comments`,
        data
      )
      return response.data.data
    },
    onSuccess: () => {
      // Invalidate comments and reviews
      queryClient.invalidateQueries({
        queryKey: reviewInteractionsKeys.comments(reviewId),
      })
      queryClient.invalidateQueries({ queryKey: reviewsKeys.all })
    },
  })
}

// ============= Delete Comment =============
export const useDeleteComment = (reviewId: number) => {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: async (commentId: number) => {
      const response = await apiClient.delete<ApiResponse<null>>(
        `/reviews/${reviewId}/comments/${commentId}`
      )
      return response.data
    },
    onSuccess: () => {
      // Invalidate comments and reviews
      queryClient.invalidateQueries({
        queryKey: reviewInteractionsKeys.comments(reviewId),
      })
      queryClient.invalidateQueries({ queryKey: reviewsKeys.all })
    },
  })
}
