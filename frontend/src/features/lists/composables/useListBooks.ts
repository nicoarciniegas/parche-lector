// Composables for List Books endpoints
import { useMutation, useQueryClient } from '@tanstack/vue-query'
import { apiClient } from '../../../shared/api/apiClient'
import type {
  ApiResponse,
  AddBookToListRequest,
} from '../../../shared/types/types'
import { listsKeys } from './useLists'

// ============= Add Book to List =============
export const useAddBookToList = (listId: number) => {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: async (data: AddBookToListRequest) => {
      const response = await apiClient.post<ApiResponse<null>>(
        `/lists/${listId}/books`,
        data
      )
      return response.data
    },
    onSuccess: () => {
      // Invalidate the specific list to refetch with new book
      queryClient.invalidateQueries({ queryKey: listsKeys.detail(listId) })
      queryClient.invalidateQueries({ queryKey: listsKeys.all })
    },
  })
}

// ============= Remove Book from List =============
export const useRemoveBookFromList = (listId: number) => {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: async (bookId: number) => {
      const response = await apiClient.delete<ApiResponse<null>>(
        `/lists/${listId}/books/${bookId}`
      )
      return response.data
    },
    onSuccess: () => {
      // Invalidate the specific list to refetch without the book
      queryClient.invalidateQueries({ queryKey: listsKeys.detail(listId) })
      queryClient.invalidateQueries({ queryKey: listsKeys.all })
    },
  })
}
