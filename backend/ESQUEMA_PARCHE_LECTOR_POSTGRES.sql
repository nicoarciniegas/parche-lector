-- PostgreSQL Schema for Parche Lector

CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  username VARCHAR(32) UNIQUE NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  password_hash VARCHAR(255) NOT NULL,
  avatar_url VARCHAR(512),
  bio TEXT,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  is_active BOOLEAN NOT NULL DEFAULT true
);

CREATE TABLE books (
  id SERIAL PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  description TEXT,
  isbn10 VARCHAR(10),
  isbn13 VARCHAR(13),
  published_year INTEGER,
  cover_url VARCHAR(512),
  language VARCHAR(10),
  page_count INTEGER,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE authors (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  sort_name VARCHAR(255),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE book_authors (
  book_id INTEGER NOT NULL,
  author_id INTEGER NOT NULL,
  role VARCHAR(64),
  position INTEGER,
  PRIMARY KEY(book_id, author_id)
);

CREATE TABLE genres (
  id SERIAL PRIMARY KEY,
  name VARCHAR(64) UNIQUE NOT NULL
);

CREATE TABLE book_genres (
  book_id INTEGER NOT NULL,
  genre_id INTEGER NOT NULL,
  PRIMARY KEY(book_id, genre_id)
);

CREATE TABLE reading_status (
  id SERIAL PRIMARY KEY,
  user_id INTEGER NOT NULL,
  book_id INTEGER NOT NULL,
  status VARCHAR(32) NOT NULL,
  progress_percent INTEGER DEFAULT 0,
  started_at DATE,
  finished_at DATE,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE reviews (
  id SERIAL PRIMARY KEY,
  user_id INTEGER NOT NULL,
  book_id INTEGER NOT NULL,
  rating DECIMAL(2,1) NOT NULL,
  title VARCHAR(140),
  body TEXT,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  is_deleted BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE review_likes (
  review_id INTEGER NOT NULL,
  user_id INTEGER NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(review_id, user_id)
);

CREATE TABLE review_comments (
  id SERIAL PRIMARY KEY,
  review_id INTEGER NOT NULL,
  user_id INTEGER NOT NULL,
  body TEXT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  is_deleted BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE library_lists (
  id SERIAL PRIMARY KEY,
  user_id INTEGER NOT NULL,
  name VARCHAR(140) NOT NULL,
  description TEXT,
  visibility VARCHAR(16) NOT NULL DEFAULT 'PUBLIC',
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE list_books (
  list_id INTEGER NOT NULL,
  book_id INTEGER NOT NULL,
  position INTEGER NOT NULL DEFAULT 1,
  note VARCHAR(255),
  added_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(list_id, book_id)
);

CREATE TABLE follows (
  follower_id INTEGER NOT NULL,
  followed_id INTEGER NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(follower_id, followed_id)
);

CREATE TABLE activity_log (
  id SERIAL PRIMARY KEY,
  user_id INTEGER NOT NULL,
  type VARCHAR(32) NOT NULL,
  subject_book_id INTEGER,
  subject_review_id INTEGER,
  subject_list_id INTEGER,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE notifications (
  id SERIAL PRIMARY KEY,
  user_id INTEGER NOT NULL,
  type VARCHAR(32) NOT NULL,
  payload JSONB,
  is_read BOOLEAN NOT NULL DEFAULT false,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE reports (
  id SERIAL PRIMARY KEY,
  reporter_id INTEGER NOT NULL,
  target_type VARCHAR(32) NOT NULL,
  target_id INTEGER NOT NULL,
  reason VARCHAR(140) NOT NULL,
  details TEXT,
  status VARCHAR(16) NOT NULL DEFAULT 'OPEN',
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  resolved_by INTEGER,
  resolved_at TIMESTAMP
);

CREATE TABLE author_follows (
  user_id INTEGER NOT NULL,
  author_id INTEGER NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(user_id, author_id)
);

CREATE TABLE favorite_books (
  user_id INTEGER NOT NULL,
  book_id INTEGER NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(user_id, book_id)
);

CREATE TABLE user_genres (
  user_id INTEGER NOT NULL,
  genre_id INTEGER NOT NULL,
  PRIMARY KEY(user_id, genre_id)
);

CREATE TABLE list_likes (
  list_id INTEGER NOT NULL,
  user_id INTEGER NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(list_id, user_id)
);

CREATE TABLE list_comments (
  id SERIAL PRIMARY KEY,
  list_id INTEGER NOT NULL,
  user_id INTEGER NOT NULL,
  body TEXT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  is_deleted BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE password_reset_tokens (
  id SERIAL PRIMARY KEY,
  user_id INTEGER NOT NULL,
  token_hash VARCHAR(255) UNIQUE NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  expires_at TIMESTAMP NOT NULL,
  used_at TIMESTAMP
);

CREATE TABLE email_verification_tokens (
  id SERIAL PRIMARY KEY,
  user_id INTEGER NOT NULL,
  token_hash VARCHAR(255) UNIQUE NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  expires_at TIMESTAMP NOT NULL,
  used_at TIMESTAMP
);

CREATE TABLE reading_events (
  id SERIAL PRIMARY KEY,
  user_id INTEGER NOT NULL,
  book_id INTEGER NOT NULL,
  event_type VARCHAR(24) NOT NULL,
  value INTEGER,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Indexes
CREATE INDEX books_title_idx ON books(title);
CREATE INDEX books_published_year_idx ON books(published_year);
CREATE UNIQUE INDEX books_isbn13_idx ON books(isbn13);
CREATE INDEX books_created_at_idx ON books(created_at);

CREATE INDEX authors_name_idx ON authors(name);

CREATE INDEX book_authors_author_id_idx ON book_authors(author_id);

CREATE INDEX book_genres_genre_id_idx ON book_genres(genre_id);

CREATE UNIQUE INDEX reading_status_user_book_idx ON reading_status(user_id, book_id);
CREATE INDEX reading_status_status_idx ON reading_status(status);

CREATE UNIQUE INDEX reviews_user_book_idx ON reviews(user_id, book_id);
CREATE INDEX reviews_book_rating_idx ON reviews(book_id, rating);
CREATE INDEX reviews_created_at_idx ON reviews(created_at);

CREATE INDEX review_comments_review_created_idx ON review_comments(review_id, created_at);

CREATE INDEX library_lists_user_name_idx ON library_lists(user_id, name);

CREATE UNIQUE INDEX list_books_list_position_idx ON list_books(list_id, position);

CREATE INDEX follows_followed_id_idx ON follows(followed_id);

CREATE INDEX activity_log_user_created_idx ON activity_log(user_id, created_at);
CREATE INDEX activity_log_type_idx ON activity_log(type);

CREATE INDEX notifications_user_read_idx ON notifications(user_id, is_read);
CREATE INDEX notifications_created_at_idx ON notifications(created_at);

CREATE INDEX list_comments_list_created_idx ON list_comments(list_id, created_at);

CREATE INDEX reading_events_user_created_idx ON reading_events(user_id, created_at);
CREATE INDEX reading_events_book_created_idx ON reading_events(book_id, created_at);

-- Foreign Keys
ALTER TABLE book_authors ADD FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE;
ALTER TABLE book_authors ADD FOREIGN KEY (author_id) REFERENCES authors(id) ON DELETE CASCADE;

ALTER TABLE book_genres ADD FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE;
ALTER TABLE book_genres ADD FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE CASCADE;

ALTER TABLE reading_status ADD FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE reading_status ADD FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE;

ALTER TABLE reviews ADD FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE reviews ADD FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE;

ALTER TABLE review_likes ADD FOREIGN KEY (review_id) REFERENCES reviews(id) ON DELETE CASCADE;
ALTER TABLE review_likes ADD FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE review_comments ADD FOREIGN KEY (review_id) REFERENCES reviews(id) ON DELETE CASCADE;
ALTER TABLE review_comments ADD FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE library_lists ADD FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE list_books ADD FOREIGN KEY (list_id) REFERENCES library_lists(id) ON DELETE CASCADE;
ALTER TABLE list_books ADD FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE;

ALTER TABLE follows ADD FOREIGN KEY (follower_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE follows ADD FOREIGN KEY (followed_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE activity_log ADD FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE activity_log ADD FOREIGN KEY (subject_book_id) REFERENCES books(id) ON DELETE SET NULL;
ALTER TABLE activity_log ADD FOREIGN KEY (subject_review_id) REFERENCES reviews(id) ON DELETE SET NULL;
ALTER TABLE activity_log ADD FOREIGN KEY (subject_list_id) REFERENCES library_lists(id) ON DELETE SET NULL;

ALTER TABLE notifications ADD FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE reports ADD FOREIGN KEY (reporter_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE reports ADD FOREIGN KEY (resolved_by) REFERENCES users(id) ON DELETE SET NULL;

ALTER TABLE author_follows ADD FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE author_follows ADD FOREIGN KEY (author_id) REFERENCES authors(id) ON DELETE CASCADE;

ALTER TABLE favorite_books ADD FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE favorite_books ADD FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE;

ALTER TABLE user_genres ADD FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE user_genres ADD FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE CASCADE;

ALTER TABLE list_likes ADD FOREIGN KEY (list_id) REFERENCES library_lists(id) ON DELETE CASCADE;
ALTER TABLE list_likes ADD FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE list_comments ADD FOREIGN KEY (list_id) REFERENCES library_lists(id) ON DELETE CASCADE;
ALTER TABLE list_comments ADD FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE password_reset_tokens ADD FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE email_verification_tokens ADD FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE reading_events ADD FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE reading_events ADD FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE;

-- Additional useful indexes for PostgreSQL
CREATE INDEX users_email_idx ON users(email);
CREATE INDEX users_username_idx ON users(username);
CREATE INDEX users_created_at_idx ON users(created_at);
