package com.parchelector.service;

import com.parchelector.dto.request.ReadingStatusRequest;
import com.parchelector.dto.response.BookResponse;
import com.parchelector.model.entity.Book;
import com.parchelector.model.entity.ReadingStatus;
import com.parchelector.model.entity.Review;
import com.parchelector.model.entity.User;
import com.parchelector.repository.BookRepository;
import com.parchelector.repository.ReadingStatusRepository;
import com.parchelector.repository.ReviewRepository;
import com.parchelector.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for book operations.
 * 
 * @author Nicolas Arciniegas
 */
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReadingStatusRepository readingStatusRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Get trending books (most reviewed/rated books).
     */
    @Transactional(readOnly = true)
    public List<BookResponse> getTrendingBooks(Long userId, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        List<Book> books = bookRepository.findAll(pageable).getContent();

        return books.stream()
                .map(book -> mapToBookResponse(book, userId))
                .collect(Collectors.toList());
    }

    /**
     * Search books by title or author.
     */
    @Transactional(readOnly = true)
    public List<BookResponse> searchBooks(String query, Long userId, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        List<Book> books = bookRepository.searchByTitleOrAuthor(query, pageable);

        return books.stream()
                .map(book -> mapToBookResponse(book, userId))
                .collect(Collectors.toList());
    }

    /**
     * Update or create reading status for a book.
     */
    @Transactional
    public void updateReadingStatus(Long userId, ReadingStatusRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        // Check if status already exists
        ReadingStatus existingStatus = readingStatusRepository.findByUserIdAndBookId(userId, request.getBookId())
                .orElse(null);

        ReadingStatus.ReadingStatusEnum statusEnum = convertToEnum(request.getStatus());

        if (existingStatus != null) {
            // Update existing status
            existingStatus.setStatus(statusEnum);
            
            if (statusEnum == ReadingStatus.ReadingStatusEnum.READING && existingStatus.getStartedAt() == null) {
                existingStatus.setStartedAt(LocalDate.now());
            }
            
            if (statusEnum == ReadingStatus.ReadingStatusEnum.READ) {
                existingStatus.setFinishedAt(LocalDate.now());
                existingStatus.setProgressPercent(100);
            }
            
            readingStatusRepository.save(existingStatus);
        } else {
            // Create new status
            ReadingStatus newStatus = new ReadingStatus();
            newStatus.setUser(user);
            newStatus.setBook(book);
            newStatus.setStatus(statusEnum);
            
            if (statusEnum == ReadingStatus.ReadingStatusEnum.READING) {
                newStatus.setStartedAt(LocalDate.now());
            }
            
            if (statusEnum == ReadingStatus.ReadingStatusEnum.READ) {
                newStatus.setStartedAt(LocalDate.now());
                newStatus.setFinishedAt(LocalDate.now());
                newStatus.setProgressPercent(100);
            }
            
            readingStatusRepository.save(newStatus);
        }
    }

    /**
     * Map Book entity to BookResponse DTO.
     */
    private BookResponse mapToBookResponse(Book book, Long userId) {
        BookResponse response = new BookResponse();
        response.setId(book.getId());
        response.setTitle(book.getTitle());
        
        // Get first author name
        String authorName = book.getAuthors().stream()
                .findFirst()
                .map(author -> author.getName())
                .orElse("Unknown Author");
        response.setAuthor(authorName);

        // Calculate average rating from reviews
        Double avgRating = reviewRepository.getAverageRatingByBookId(book.getId());
        response.setRating(avgRating != null ? avgRating : 0.0);

        response.setCover(book.getCoverUrl());

        // Get user's reading status if exists
        if (userId != null) {
            readingStatusRepository.findByUserIdAndBookId(userId, book.getId())
                    .ifPresent(status -> {
                        String frontendStatus = convertStatusToFrontend(status.getStatus());
                        response.setStatus(frontendStatus);
                    });
        }

        return response;
    }

    /**
     * Convert frontend status string to backend enum.
     */
    private ReadingStatus.ReadingStatusEnum convertToEnum(String status) {
        switch (status.toUpperCase()) {
            case "READING":
                return ReadingStatus.ReadingStatusEnum.READING;
            case "READ":
                return ReadingStatus.ReadingStatusEnum.READ;
            case "WANT_TO_READ":
                return ReadingStatus.ReadingStatusEnum.WANT_TO_READ;
            default:
                throw new IllegalArgumentException("Invalid status: " + status);
        }
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
                return null;
        }
    }
}
