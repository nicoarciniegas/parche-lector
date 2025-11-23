// Composables for Profile endpoints
import { useQuery, useMutation, useQueryClient } from '@tanstack/vue-query'
import { apiClient } from '../../../shared/api/apiClient'
import type {
  ApiResponse,
  User,
  UpdateProfileRequest,
} from '../../../shared/types/types'

// ============= Query Keys =============
export const profileKeys = {
  all: ['profile'] as const,
  me: () => [...profileKeys.all, 'me'] as const,
  user: (userId: number) => [...profileKeys.all, 'user', userId] as const,
}

// ============= Get My Profile =============
export const useMyProfile = () => {
  return useQuery({
    queryKey: profileKeys.me(),
    queryFn: async () => {
      const response = await apiClient.get<ApiResponse<User>>('/auth/me')
      return response.data.data
    },
  })
}

// ============= Update Profile =============
export const useUpdateProfile = () => {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: async (data: UpdateProfileRequest) => {
      const response = await apiClient.put<ApiResponse<User>>(
        '/auth/update',
        data
      )
      return response.data.data
    },
    onSuccess: () => {
      // Invalidate my profile
      queryClient.invalidateQueries({ queryKey: profileKeys.me() })
    },
  })
}

// ============= Get User Profile =============
export const useUserProfile = (userId: number) => {
  return useQuery({
    queryKey: profileKeys.user(userId),
    queryFn: async () => {
      const response = await apiClient.get<ApiResponse<User>>(
        `/users/${userId}`
      )
      return response.data.data
    },
  })
}
