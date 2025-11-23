// Composables for Reading Status endpoints
import { useMutation, useQueryClient } from '@tanstack/vue-query'
import { apiClient } from '../../../shared/api/apiClient'
import type {
  ApiResponse,
  ReadingStatusRequest,
} from '../../../shared/types/types'

// ============= Update Reading Status =============
export const useUpdateReadingStatus = () => {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: async (data: ReadingStatusRequest) => {
      const response = await apiClient.post<ApiResponse<null>>(
        '/books/reading-status',
        data
      )
      return response.data
    },
    onSuccess: () => {
      // Invalidate books queries to refetch with updated status
      queryClient.invalidateQueries({ queryKey: ['books'] })
      queryClient.invalidateQueries({ queryKey: ['profile'] })
    },
  })
}
