// Composables for Books endpoints
import { useQuery } from '@tanstack/vue-query'
import { apiClient } from '../../../shared/api/apiClient'
import type {
  ApiResponse,
  Book,
  BookFilterParams,
} from '../../../shared/types/types'
import type { Ref } from 'vue'

// ============= Query Keys =============
export const booksKeys = {
  all: ['books'] as const,
  trending: (limit?: number) => [...booksKeys.all, 'trending', limit] as const,
  search: (query: string, limit?: number) =>
    [...booksKeys.all, 'search', query, limit] as const,
  filter: (params: BookFilterParams) =>
    [...booksKeys.all, 'filter', params] as const,
}

// ============= Trending Books =============
export const useTrendingBooks = (limit?: Ref<number> | number) => {
  return useQuery({
    queryKey: booksKeys.trending(
      typeof limit === 'number' ? limit : limit?.value
    ),
    queryFn: async () => {
      const limitValue = typeof limit === 'number' ? limit : limit?.value || 20
      const response = await apiClient.get<ApiResponse<Book[]>>(
        `/books/trending`,
        {
          params: { limit: limitValue },
        }
      )
      return response.data.data
    },
  })
}

// ============= Search Books =============
export const useSearchBooks = (
  query: Ref<string> | string,
  limit?: Ref<number> | number
) => {
  return useQuery({
    queryKey: booksKeys.search(
      typeof query === 'string' ? query : query.value,
      typeof limit === 'number' ? limit : limit?.value
    ),
    queryFn: async () => {
      const queryValue = typeof query === 'string' ? query : query.value
      const limitValue = typeof limit === 'number' ? limit : limit?.value || 20

      if (!queryValue) {
        return []
      }

      const response = await apiClient.get<ApiResponse<Book[]>>(
        `/books/search`,
        {
          params: { query: queryValue, limit: limitValue },
        }
      )
      return response.data.data
    },
    enabled: () => {
      const queryValue = typeof query === 'string' ? query : query.value
      return !!queryValue
    },
  })
}

// ============= Filter Books =============
export const useFilterBooks = (params: Ref<BookFilterParams>) => {
  return useQuery({
    queryKey: booksKeys.filter(params.value),
    queryFn: async () => {
      const response = await apiClient.get<ApiResponse<Book[]>>(
        `/books/filter`,
        {
          params: params.value,
        }
      )
      return response.data.data
    },
  })
}
