package com.parchelector.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for adding a book to favorites.
 * 
 * @author Nicolas Arciniegas
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteBookRequest {

    @NotNull(message = "Book ID is required")
    private Long bookId;
}
