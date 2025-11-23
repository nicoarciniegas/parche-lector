// Composables for User Activity endpoint
import { useQuery } from '@tanstack/vue-query'
import { apiClient } from '../../../shared/api/apiClient'
import type { ApiResponse, ActivityResponse } from '../../../shared/types/types'

// ============= Query Keys =============
export const activityKeys = {
  all: ['activity'] as const,
  my: () => [...activityKeys.all, 'my'] as const,
}

// ============= Get My Activity =============
export const useActivity = () => {
  return useQuery({
    queryKey: activityKeys.my(),
    queryFn: async () => {
      const response = await apiClient.get<ApiResponse<ActivityResponse>>(
        '/auth/activity'
      )
      return response.data.data
    },
  })
}
