package ru.khamzin.movie.service;

import org.springframework.stereotype.Service;
import ru.khamzin.movie.dto.ReviewDto;

import java.util.List;

@Service
public interface ReviewService {

    ReviewDto createReview(int movieId, ReviewDto reviewDto);

    List<ReviewDto> getReviewsByMovieId(int movieId);

    ReviewDto getReviewById(int reviewId, int movieId);

    ReviewDto updateReview(int movieId, int reviewId, ReviewDto reviewDto);

    void deleteReview(int movieId, int reviewId);
}
