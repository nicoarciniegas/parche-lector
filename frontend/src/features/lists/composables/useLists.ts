// Composables for Lists endpoints
import { useQuery, useMutation, useQueryClient } from '@tanstack/vue-query'
import { apiClient } from '../../../shared/api/apiClient'
import type {
  ApiResponse,
  ReadList,
  CreateListRequest,
  UpdateListRequest,
} from '../../../shared/types/types'

// ============= Query Keys =============
export const listsKeys = {
  all: ['lists'] as const,
  detail: (id: number) => [...listsKeys.all, 'detail', id] as const,
}

// ============= Get List by ID =============
export const useList = (id: number) => {
  return useQuery({
    queryKey: listsKeys.detail(id),
    queryFn: async () => {
      const response = await apiClient.get<ApiResponse<ReadList>>(
        `/lists/${id}`
      )
      return response.data.data
    },
  })
}

// ============= Create List =============
export const useCreateList = () => {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: async (data: CreateListRequest) => {
      const response = await apiClient.post<ApiResponse<ReadList>>(
        '/lists',
        data
      )
      return response.data.data
    },
    onSuccess: () => {
      // Invalidate lists queries
      queryClient.invalidateQueries({ queryKey: listsKeys.all })
      queryClient.invalidateQueries({ queryKey: ['profile'] })
    },
  })
}

// ============= Update List =============
export const useUpdateList = (id: number) => {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: async (data: UpdateListRequest) => {
      const response = await apiClient.put<ApiResponse<ReadList>>(
        `/lists/${id}`,
        data
      )
      return response.data.data
    },
    onSuccess: () => {
      // Invalidate specific list and all lists
      queryClient.invalidateQueries({ queryKey: listsKeys.detail(id) })
      queryClient.invalidateQueries({ queryKey: listsKeys.all })
    },
  })
}

// ============= Delete List =============
export const useDeleteList = () => {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: async (id: number) => {
      const response = await apiClient.delete<ApiResponse<null>>(
        `/lists/${id}`
      )
      return response.data
    },
    onSuccess: () => {
      // Invalidate all lists
      queryClient.invalidateQueries({ queryKey: listsKeys.all })
      queryClient.invalidateQueries({ queryKey: ['profile'] })
    },
  })
}
