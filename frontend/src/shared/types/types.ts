// Shared TypeScript types for the application

// ============= API Response Types =============
export interface ApiResponse<T> {
  status: 'SUCCESS' | 'ERROR'
  message: string
  data: T
}

// ============= Auth Types =============
export interface LoginRequest {
  usernameOrEmail: string
  password: string
}

export interface RegisterRequest {
  username: string
  email: string
  password: string
}

export interface AuthResponse {
  token: string
  username: string
}

export interface ForgotPasswordRequest {
  email: string
}

export interface ResetPasswordRequest {
  token: string
  newPassword: string
}

export interface UpdateProfileRequest {
  username?: string
  bio?: string
  avatarUrl?: string
}

// ============= User Types =============
export interface User {
  id: number
  userName: string
  userAvatar: string
  bio: string
  followers: number
  following: number
  userBooks: UserBook[]
}

export interface UserBook {
  id: number
  title: string
  author: string
  rating: number
  cover: string
  status: string
}

export interface UserStats {
  userId: number
  username: string
  followersCount: number
  followingCount: number
  isFollowing: boolean | null
}

// ============= Book Types =============
export interface Book {
  id: number
  title: string
  author: string
  rating: number
  cover: string
  status: string | null
  averageRating?: number
  coverUrl?: string
  readingStatus?: ReadingStatus
}

export type ReadingStatus = 'READING' | 'READ' | 'WANT_TO_READ'

export interface ReadingStatusRequest {
  bookId: number
  status: ReadingStatus
}

export interface BookFilterParams {
  genre?: string
  minYear?: number
  maxYear?: number
  sortBy?: 'popular' | 'rating' | 'newest' | 'oldest'
  limit?: number
}

export interface FavoriteRequest {
  bookId: number
}

// ============= List Types =============
export type ListVisibility = 'PUBLIC' | 'PRIVATE' | 'FOLLOWERS_ONLY'

export interface CreateListRequest {
  name: string
  description: string
  visibility: ListVisibility
}

export interface UpdateListRequest {
  name?: string
  description?: string
  visibility?: ListVisibility
}

export interface ReadList {
  id: number
  name: string
  description: string
  visibility: ListVisibility
  userId: number
  username: string
  createdAt: string
  updatedAt: string
  bookCount: number
  likeCount: number
  books: ListBook[]
}

export interface ListBook {
  bookId: number
  title: string
  coverUrl: string
  authors: string[]
  position: number
  note: string | null
  addedAt: string
}

export interface AddBookToListRequest {
  bookId: number
  position?: number
  note?: string
}

// ============= Review Types =============
export interface CreateReviewRequest {
  bookId: number
  rating: number
  title?: string
  body?: string
}

export interface UpdateReviewRequest {
  rating?: number
  title?: string
  body?: string
}

export interface Review {
  id: number
  bookId: number
  bookTitle: string
  bookCover: string
  userId: number
  username: string
  userAvatar: string
  rating: number
  title: string
  body: string
  createdAt: string
  updatedAt: string
  likes: number
  comments: number
}

export interface BookReviewsResponse {
  bookId: number
  bookTitle: string
  averageRating: number
  totalReviews: number
  reviews: Review[]
}

export interface AddCommentRequest {
  body: string
}

export interface Comment {
  id: number
  reviewId: number
  userId: number
  username: string
  userAvatar: string
  body: string
  createdAt: string
}

// ============= Social Types =============
export interface FollowUserRequest {
  userId: number
}

export interface FollowAuthorRequest {
  authorId: number
}

export interface FollowUserResponse {
  followerId: number
  followerUsername: string
  followedId: number
  followedUsername: string
  createdAt: string
}

export interface FeedParams {
  limit?: number
  offset?: number
}

export interface FeedResponse {
  items: FeedItem[]
  total: number
  limit: number
  offset: number
  hasMore: boolean
}

export interface FeedItem {
  type: 'REVIEW' | 'LIST'
  userId: number
  username: string
  userAvatar: string
  createdAt: string
  review: FeedReview | null
  list: FeedList | null
}

export interface FeedReview {
  reviewId: number
  bookId: number
  bookTitle: string
  bookCover: string
  rating: number
  title: string
  body: string
  likes: number
  comments: number
}

export interface FeedList {
  listId: number
  name: string
  description: string
  visibility: ListVisibility
  bookCount: number
  likes: number
}

// ============= Activity Types =============
export interface ActivityStats {
  totalReviews: number
  totalReadLists: number
  booksRead: number
  booksReading: number
  booksToRead: number
  averageRating: number
}

export interface ActivityResponse {
  stats: ActivityStats
  recentReviews: Review[]
  readLists: ReadList[]
}
