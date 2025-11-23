package com.parchelector.service;

import com.parchelector.dto.response.UserActivityResponse;
import com.parchelector.dto.response.UserActivityResponse.ActivityStats;
import com.parchelector.dto.response.UserActivityResponse.ReadListActivity;
import com.parchelector.dto.response.UserActivityResponse.ReviewActivity;
import com.parchelector.model.entity.LibraryList;
import com.parchelector.model.entity.ReadingStatus.ReadingStatusEnum;
import com.parchelector.model.entity.Review;
import com.parchelector.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for user activity operations.
 * 
 * @author Nicolas Arciniegas
 */
@Service
public class ActivityService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private LibraryListRepository libraryListRepository;

    @Autowired
    private ReadingStatusRepository readingStatusRepository;

    @Autowired
    private ListLikeRepository listLikeRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Get user activity including reviews, read lists, and stats.
     */
    @Transactional(readOnly = true)
    public UserActivityResponse getUserActivity(Long userId) {
        // Get stats
        ActivityStats stats = getActivityStats(userId);

        // Get recent reviews
        List<Review> reviews = reviewRepository.findByUserIdOrderByCreatedAtDesc(userId);
        List<ReviewActivity> reviewActivities = reviews.stream()
                .limit(10) // Last 10 reviews
                .map(this::mapToReviewActivity)
                .collect(Collectors.toList());

        // Get read lists
        List<LibraryList> lists = libraryListRepository.findByUserIdOrderByCreatedAtDesc(userId);
        List<ReadListActivity> readListActivities = lists.stream()
                .map(this::mapToReadListActivity)
                .collect(Collectors.toList());

        return new UserActivityResponse(stats, reviewActivities, readListActivities);
    }

    private ActivityStats getActivityStats(Long userId) {
        int totalReviews = reviewRepository.countByUserId(userId);
        int totalReadLists = (int) libraryListRepository.findByUserIdOrderByCreatedAtDesc(userId).size();
        
        int booksRead = readingStatusRepository.countByUserIdAndStatus(userId, ReadingStatusEnum.READ);
        int booksReading = readingStatusRepository.countByUserIdAndStatus(userId, ReadingStatusEnum.READING);
        int booksToRead = readingStatusRepository.countByUserIdAndStatus(userId, ReadingStatusEnum.WANT_TO_READ);
        
        Double averageRating = reviewRepository.getAverageRatingByUserId(userId);
        if (averageRating == null) {
            averageRating = 0.0;
        }

        return new ActivityStats(
                totalReviews,
                totalReadLists,
                booksRead,
                booksReading,
                booksToRead,
                averageRating
        );
    }

    private ReviewActivity mapToReviewActivity(Review review) {
        int likes = reviewRepository.countLikesByReviewId(review.getId());
        int comments = reviewRepository.countCommentsByReviewId(review.getId());

        return new ReviewActivity(
                review.getId(),
                review.getBook().getId(),
                review.getBook().getTitle(),
                review.getBook().getCoverUrl(),
                review.getRating() != null ? review.getRating().doubleValue() : 0.0,
                review.getTitle(),
                review.getBody(),
                review.getCreatedAt().format(DATE_FORMATTER),
                likes,
                comments
        );
    }

    private ReadListActivity mapToReadListActivity(LibraryList list) {
        int bookCount = libraryListRepository.countBooksByListId(list.getId());
        int likes = listLikeRepository.countByListId(list.getId());

        return new ReadListActivity(
                list.getId(),
                list.getName(),
                list.getDescription(),
                list.getVisibility(),
                bookCount,
                list.getCreatedAt().format(DATE_FORMATTER),
                likes
        );
    }
}
