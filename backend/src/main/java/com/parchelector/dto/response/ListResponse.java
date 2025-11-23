package com.parchelector.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for list details response.
 * 
 * @author Nicolas Arciniegas
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListResponse {

    private Long id;
    private String name;
    private String description;
    private String visibility;
    private Long userId;
    private String username;
    private String createdAt;
    private String updatedAt;
    private int bookCount;
    private int likeCount;
    private List<BookInList> books;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookInList {
        private Long bookId;
        private String title;
        private String coverUrl;
        private List<String> authors;
        private Integer position;
        private String note;
        private String addedAt;
    }
}
