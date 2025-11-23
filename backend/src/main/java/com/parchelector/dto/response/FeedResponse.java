package com.parchelector.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Response DTO for social feed containing activities from followed users.
 * 
 * @author Nicolas Arciniegas
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedResponse {
    
    private List<FeedItem> items;
    private Integer total;
    private Integer limit;
    private Integer offset;
    private Boolean hasMore;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FeedItem {
        private String type; // "REVIEW" or "LIST"
        private Long userId;
        private String username;
        private String userAvatar;
        private LocalDateTime createdAt;
        private ReviewData review;
        private ListData list;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewData {
        private Long reviewId;
        private Long bookId;
        private String bookTitle;
        private String bookCover;
        private Double rating;
        private String title;
        private String body;
        private Integer likes;
        private Integer comments;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListData {
        private Long listId;
        private String name;
        private String description;
        private String visibility;
        private Integer bookCount;
        private Integer likes;
    }
}
