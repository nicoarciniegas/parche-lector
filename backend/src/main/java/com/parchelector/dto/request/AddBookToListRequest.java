package com.parchelector.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for adding a book to a reading list.
 * 
 * @author Nicolas Arciniegas
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddBookToListRequest {

    @NotNull(message = "Book ID is required")
    private Long bookId;

    private Integer position;

    @Size(max = 255, message = "Note must be 255 characters or less")
    private String note;
}
