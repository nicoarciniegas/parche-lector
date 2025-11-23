package com.parchelector.controller;

import com.parchelector.dto.ApiResponse;
import com.parchelector.dto.request.ReadingStatusRequest;
import com.parchelector.dto.response.BookResponse;
import com.parchelector.model.entity.User;
import com.parchelector.repository.UserRepository;
import com.parchelector.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for book-related endpoints.
 * 
 * @author Nicolas Arciniegas
 */
@RestController
@RequestMapping("/books")
@Tag(name = "Books", description = "Endpoints for book management and discovery")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Get trending books from the community.
     */
    @GetMapping("/trending")
    @Operation(summary = "Get trending books", description = "Get popular books from the community")
    @SecurityRequirement(name = "bearer-jwt")
    public ResponseEntity<ApiResponse<List<BookResponse>>> getTrendingBooks(
            @RequestParam(defaultValue = "20") int limit) {
        try {
            Long userId = getCurrentUserId();
            List<BookResponse> books = bookService.getTrendingBooks(userId, limit);
            
            ApiResponse<List<BookResponse>> response = new ApiResponse<>(
                    "SUCCESS",
                    "Books retrieved successfully",
                    books
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<BookResponse>> response = new ApiResponse<>(
                    "ERROR",
                    e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Search books by title or author.
     */
    @GetMapping("/search")
    @Operation(summary = "Search books", description = "Search books by title or author name")
    @SecurityRequirement(name = "bearer-jwt")
    public ResponseEntity<ApiResponse<List<BookResponse>>> searchBooks(
            @RequestParam String query,
            @RequestParam(defaultValue = "20") int limit) {
        try {
            Long userId = getCurrentUserId();
            List<BookResponse> books = bookService.searchBooks(query, userId, limit);
            
            ApiResponse<List<BookResponse>> response = new ApiResponse<>(
                    "SUCCESS",
                    "Search completed successfully",
                    books
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<BookResponse>> response = new ApiResponse<>(
                    "ERROR",
                    e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Update reading status for a book.
     */
    @PostMapping("/reading-status")
    @Operation(summary = "Update reading status", description = "Add or update reading status for a book")
    @SecurityRequirement(name = "bearer-jwt")
    public ResponseEntity<ApiResponse<Void>> updateReadingStatus(
            @Valid @RequestBody ReadingStatusRequest request) {
        try {
            Long userId = getCurrentUserId();
            bookService.updateReadingStatus(userId, request);
            
            ApiResponse<Void> response = new ApiResponse<>(
                    "SUCCESS",
                    "Reading status updated successfully",
                    null
            );
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponse<Void> response = new ApiResponse<>(
                    "ERROR",
                    e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ApiResponse<Void> response = new ApiResponse<>(
                    "ERROR",
                    "Failed to update reading status",
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Get all favorite books for current user.
     */
    @GetMapping("/favorites")
    @Operation(summary = "Get user's favorite books", security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<ApiResponse<List<BookResponse>>> getFavorites() {
        try {
            Long userId = getCurrentUserId();
            List<BookResponse> favorites = bookService.getUserFavorites(userId);
            
            ApiResponse<List<BookResponse>> response = new ApiResponse<>(
                    "SUCCESS",
                    "Favorites retrieved successfully",
                    favorites
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<BookResponse>> response = new ApiResponse<>(
                    "ERROR",
                    e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Add a book to favorites.
     */
    @PostMapping("/favorites")
    @Operation(summary = "Add a book to favorites", security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<ApiResponse<Void>> addFavorite(@Valid @RequestBody com.parchelector.dto.request.FavoriteBookRequest request) {
        try {
            Long userId = getCurrentUserId();
            bookService.addFavorite(userId, request);
            
            ApiResponse<Void> response = new ApiResponse<>(
                    "SUCCESS",
                    "Book added to favorites successfully",
                    null
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            ApiResponse<Void> response = new ApiResponse<>(
                    "ERROR",
                    e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ApiResponse<Void> response = new ApiResponse<>(
                    "ERROR",
                    e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Remove a book from favorites.
     */
    @DeleteMapping("/favorites/{bookId}")
    @Operation(summary = "Remove a book from favorites", security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<ApiResponse<Void>> removeFavorite(@PathVariable Long bookId) {
        try {
            Long userId = getCurrentUserId();
            bookService.removeFavorite(userId, bookId);
            
            ApiResponse<Void> response = new ApiResponse<>(
                    "SUCCESS",
                    "Book removed from favorites successfully",
                    null
            );
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponse<Void> response = new ApiResponse<>(
                    "ERROR",
                    e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ApiResponse<Void> response = new ApiResponse<>(
                    "ERROR",
                    e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Get current authenticated user ID.
     */
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return user.getId();
    }
}
