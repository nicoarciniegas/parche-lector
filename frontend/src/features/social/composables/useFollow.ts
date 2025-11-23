// Composables for Social Follow endpoints
import { useQuery, useMutation, useQueryClient } from '@tanstack/vue-query'
import { apiClient } from '../../../shared/api/apiClient'
import type {
  ApiResponse,
  FollowUserRequest,
  FollowAuthorRequest,
  FollowUserResponse,
  UserStats,
} from '../../../shared/types/types'

// ============= Query Keys =============
export const followKeys = {
  userStats: (userId: number) => ['userStats', userId] as const,
  userStatus: (userId: number) => ['followUserStatus', userId] as const,
  authorStatus: (authorId: number) => ['followAuthorStatus', authorId] as const,
}

// ============= Follow User =============
export const useFollowUser = () => {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: async (data: FollowUserRequest) => {
      const response = await apiClient.post<ApiResponse<FollowUserResponse>>(
        '/social/follow/user',
        data
      )
      return response.data.data
    },
    onSuccess: (_data, variables) => {
      // Invalidate user stats and follow status
      queryClient.invalidateQueries({
        queryKey: followKeys.userStats(variables.userId),
      })
      queryClient.invalidateQueries({
        queryKey: followKeys.userStatus(variables.userId),
      })
      queryClient.invalidateQueries({ queryKey: ['profile'] })
    },
  })
}

// ============= Unfollow User =============
export const useUnfollowUser = () => {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: async (userId: number) => {
      const response = await apiClient.delete<ApiResponse<null>>(
        `/social/follow/user/${userId}`
      )
      return response.data
    },
    onSuccess: (_data, userId) => {
      // Invalidate user stats and follow status
      queryClient.invalidateQueries({
        queryKey: followKeys.userStats(userId),
      })
      queryClient.invalidateQueries({
        queryKey: followKeys.userStatus(userId),
      })
      queryClient.invalidateQueries({ queryKey: ['profile'] })
    },
  })
}

// ============= Follow Author =============
export const useFollowAuthor = () => {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: async (data: FollowAuthorRequest) => {
      const response = await apiClient.post<ApiResponse<null>>(
        '/social/follow/author',
        data
      )
      return response.data
    },
    onSuccess: (_data, variables) => {
      // Invalidate author follow status
      queryClient.invalidateQueries({
        queryKey: followKeys.authorStatus(variables.authorId),
      })
    },
  })
}

// ============= Unfollow Author =============
export const useUnfollowAuthor = () => {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: async (authorId: number) => {
      const response = await apiClient.delete<ApiResponse<null>>(
        `/social/follow/author/${authorId}`
      )
      return response.data
    },
    onSuccess: (_data, authorId) => {
      // Invalidate author follow status
      queryClient.invalidateQueries({
        queryKey: followKeys.authorStatus(authorId),
      })
    },
  })
}

// ============= Get User Stats =============
export const useUserStats = (userId: number) => {
  return useQuery({
    queryKey: followKeys.userStats(userId),
    queryFn: async () => {
      const response = await apiClient.get<ApiResponse<UserStats>>(
        `/social/users/${userId}/stats`
      )
      return response.data.data
    },
  })
}

// ============= Get Follow User Status =============
export const useFollowUserStatus = (userId: number) => {
  return useQuery({
    queryKey: followKeys.userStatus(userId),
    queryFn: async () => {
      const response = await apiClient.get<ApiResponse<boolean>>(
        `/social/follow/user/${userId}/status`
      )
      return response.data.data
    },
  })
}

// ============= Get Follow Author Status =============
export const useFollowAuthorStatus = (authorId: number) => {
  return useQuery({
    queryKey: followKeys.authorStatus(authorId),
    queryFn: async () => {
      const response = await apiClient.get<ApiResponse<boolean>>(
        `/social/follow/author/${authorId}/status`
      )
      return response.data.data
    },
  })
}
