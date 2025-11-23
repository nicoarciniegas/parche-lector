// Composables for Social Feed endpoint
import { useInfiniteQuery } from '@tanstack/vue-query'
import { apiClient } from '../../../shared/api/apiClient'
import type { ApiResponse, FeedResponse, FeedParams } from '../../../shared/types/types'
import type { Ref } from 'vue'

// ============= Query Keys =============
export const feedKeys = {
  all: ['social', 'feed'] as const,
  list: (params: FeedParams) => [...feedKeys.all, params] as const,
}

// ============= Get Social Feed with Infinite Scroll =============
export const useFeed = (limit: Ref<number> | number = 20) => {
  const limitValue = typeof limit === 'number' ? limit : limit.value

  return useInfiniteQuery({
    queryKey: feedKeys.list({ limit: limitValue }),
    queryFn: async ({ pageParam = 0 }) => {
      const response = await apiClient.get<ApiResponse<FeedResponse>>(
        '/social/feed',
        {
          params: {
            limit: limitValue,
            offset: pageParam,
          },
        }
      )
      return response.data.data
    },
    getNextPageParam: (lastPage) => {
      // If there are more items, return the next offset
      if (lastPage.hasMore) {
        return lastPage.offset + lastPage.limit
      }
      // No more pages
      return undefined
    },
    initialPageParam: 0,
  })
}
