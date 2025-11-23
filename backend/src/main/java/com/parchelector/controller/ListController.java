package com.parchelector.controller;

import com.parchelector.dto.ApiResponse;
import com.parchelector.dto.request.AddBookToListRequest;
import com.parchelector.dto.request.CreateListRequest;
import com.parchelector.dto.request.UpdateListRequest;
import com.parchelector.dto.response.ListResponse;
import com.parchelector.model.entity.User;
import com.parchelector.repository.UserRepository;
import com.parchelector.service.ListService;
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

/**
 * REST controller for reading list management.
 * 
 * @author Nicolas Arciniegas
 */
@RestController
@RequestMapping("/lists")
@Tag(name = "Lists", description = "Endpoints for managing reading lists")
public class ListController {

    @Autowired
    private ListService listService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Create a new reading list.
     */
    @PostMapping
    @Operation(summary = "Create a new reading list", security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<ApiResponse<ListResponse>> createList(@Valid @RequestBody CreateListRequest request) {
        try {
            Long userId = getCurrentUserId();
            ListResponse list = listService.createList(userId, request);
            
            ApiResponse<ListResponse> response = new ApiResponse<>(
                    "SUCCESS",
                    "List created successfully",
                    list
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ApiResponse<ListResponse> response = new ApiResponse<>(
                    "ERROR",
                    e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Get list details by ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get list details", security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<ApiResponse<ListResponse>> getList(@PathVariable Long id) {
        try {
            Long userId = getCurrentUserId();
            ListResponse list = listService.getListById(id, userId);
            
            ApiResponse<ListResponse> response = new ApiResponse<>(
                    "SUCCESS",
                    "List retrieved successfully",
                    list
            );
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponse<ListResponse> response = new ApiResponse<>(
                    "ERROR",
                    e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ApiResponse<ListResponse> response = new ApiResponse<>(
                    "ERROR",
                    e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Update an existing reading list.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a reading list", security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<ApiResponse<ListResponse>> updateList(
            @PathVariable Long id,
            @Valid @RequestBody UpdateListRequest request) {
        try {
            Long userId = getCurrentUserId();
            ListResponse list = listService.updateList(userId, id, request);
            
            ApiResponse<ListResponse> response = new ApiResponse<>(
                    "SUCCESS",
                    "List updated successfully",
                    list
            );
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponse<ListResponse> response = new ApiResponse<>(
                    "ERROR",
                    e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ApiResponse<ListResponse> response = new ApiResponse<>(
                    "ERROR",
                    e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Delete a reading list.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a reading list", security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<ApiResponse<Void>> deleteList(@PathVariable Long id) {
        try {
            Long userId = getCurrentUserId();
            listService.deleteList(userId, id);
            
            ApiResponse<Void> response = new ApiResponse<>(
                    "SUCCESS",
                    "List deleted successfully",
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
     * Add a book to a reading list.
     */
    @PostMapping("/{id}/books")
    @Operation(summary = "Add a book to a reading list", security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<ApiResponse<Void>> addBookToList(
            @PathVariable Long id,
            @Valid @RequestBody AddBookToListRequest request) {
        try {
            Long userId = getCurrentUserId();
            listService.addBookToList(userId, id, request);
            
            ApiResponse<Void> response = new ApiResponse<>(
                    "SUCCESS",
                    "Book added to list successfully",
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
     * Remove a book from a reading list.
     */
    @DeleteMapping("/{id}/books/{bookId}")
    @Operation(summary = "Remove a book from a reading list", security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<ApiResponse<Void>> removeBookFromList(
            @PathVariable Long id,
            @PathVariable Long bookId) {
        try {
            Long userId = getCurrentUserId();
            listService.removeBookFromList(userId, id, bookId);
            
            ApiResponse<Void> response = new ApiResponse<>(
                    "SUCCESS",
                    "Book removed from list successfully",
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
