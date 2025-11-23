package com.parchelector.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for user activity data.
 * 
 * @author Nicolas Arciniegas
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserActivityResponse {
    
    private ActivityStats stats;
    private List<ReviewActivity> recentReviews;
    private List<ReadListActivity> readLists;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ActivityStats {
        private int totalReviews;
        private int totalReadLists;
        private int booksRead;
        private int booksReading;
        private int booksToRead;
        private double averageRating;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewActivity {
        private Long id;
        private Long bookId;
        private String bookTitle;
        private String bookCover;
        private Double rating;
        private String title;
        private String body;
        private String createdAt;
        private int likes;
        private int comments;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReadListActivity {
        private Long id;
        private String name;
        private String description;
        private String visibility;
        private int bookCount;
        private String createdAt;
        private int likes;
    }
}
