package com.parchelector.service;

import com.parchelector.dto.response.UserBookResponse;
import com.parchelector.dto.response.UserProfileResponse;
import com.parchelector.model.entity.ReadingStatus;
import com.parchelector.model.entity.Review;
import com.parchelector.model.entity.User;
import com.parchelector.repository.FollowRepository;
import com.parchelector.repository.ReadingStatusRepository;
import com.parchelector.repository.ReviewRepository;
import com.parchelector.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for user operations.
 * 
 * @author Nicolas Arciniegas
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private ReadingStatusRepository readingStatusRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    /**
     * Get user profile with books and statistics.
     */
    @Transactional(readOnly = true)
    public UserProfileResponse getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Get follower and following counts
        long followers = followRepository.countFollowersByUserId(userId);
        long following = followRepository.countFollowingByUserId(userId);

        // Get user's books with reading status
        List<ReadingStatus> readingStatuses = readingStatusRepository.findByUserIdWithBooks(userId);

        List<UserBookResponse> userBooks = readingStatuses.stream()
                .map(rs -> {
                    UserBookResponse bookResponse = new UserBookResponse();
                    bookResponse.setId(rs.getBook().getId());
                    bookResponse.setTitle(rs.getBook().getTitle());
                    
                    // Get first author
                    String authorName = rs.getBook().getAuthors().stream()
                            .findFirst()
                            .map(author -> author.getName())
                            .orElse("Unknown");
                    bookResponse.setAuthor(authorName);

                    // Get user's review rating for this book
                    Double rating = reviewRepository.findByUserIdAndBookId(userId, rs.getBook().getId())
                            .map(review -> review.getRating().doubleValue())
                            .orElse(0.0);
                    bookResponse.setRating(rating);

                    bookResponse.setCover(rs.getBook().getCoverUrl());

                    // Convert status enum to frontend format
                    String status = convertStatusToFrontend(rs.getStatus());
                    bookResponse.setStatus(status);

                    return bookResponse;
                })
                .collect(Collectors.toList());

        UserProfileResponse profile = new UserProfileResponse();
        profile.setUserName(user.getUsername());
        profile.setUserAvatar(user.getAvatarUrl());
        profile.setBio(user.getBio());
        profile.setFollowers(followers);
        profile.setFollowing(following);
        profile.setUserBooks(userBooks);

        return profile;
    }

    /**
     * Convert backend status enum to frontend status string.
     */
    private String convertStatusToFrontend(ReadingStatus.ReadingStatusEnum status) {
        switch (status) {
            case READING:
                return "leyendo";
            case READ:
                return "leido";
            case WANT_TO_READ:
                return "por_leer";
            default:
                return "por_leer";
        }
    }
}
