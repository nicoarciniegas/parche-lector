package com.parchelector.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for user book information in profile.
 * 
 * @author Nicolas Arciniegas
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBookResponse {
    private Long id;
    private String title;
    private String author;
    private Double rating;
    private String cover;
    private String status; // "leyendo", "leido", "por_leer"
}
