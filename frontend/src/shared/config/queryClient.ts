// TanStack Query client configuration
import { QueryClient } from '@tanstack/vue-query'

export const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      // Queries will be considered stale after 5 minutes
      staleTime: 5 * 60 * 1000,
      // Cache data for 10 minutes
      gcTime: 10 * 60 * 1000,
      // Retry failed requests once
      retry: 1,
      // Don't refetch on window focus by default
      refetchOnWindowFocus: false,
    },
    mutations: {
      // Retry failed mutations once
      retry: 1,
    },
  },
})
