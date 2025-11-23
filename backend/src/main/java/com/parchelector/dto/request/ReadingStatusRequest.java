package com.parchelector.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for updating reading status.
 * 
 * @author Nicolas Arciniegas
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadingStatusRequest {
    
    @NotNull(message = "Book ID is required")
    private Long bookId;
    
    @NotNull(message = "Status is required")
    private String status; // "READING", "READ", "WANT_TO_READ"
}
