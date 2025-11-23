package com.parchelector.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for updating an existing reading list.
 * 
 * @author Nicolas Arciniegas
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateListRequest {

    @Size(max = 140, message = "List name must be 140 characters or less")
    private String name;

    @Size(max = 1000, message = "Description must be 1000 characters or less")
    private String description;

    private String visibility; // PUBLIC, PRIVATE, FOLLOWERS_ONLY
}
