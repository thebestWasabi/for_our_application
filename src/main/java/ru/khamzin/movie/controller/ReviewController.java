package ru.khamzin.movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.khamzin.movie.dto.ReviewDto;
import ru.khamzin.movie.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/{movieId}/reviews")
    public ResponseEntity<ReviewDto> createReview(
            @PathVariable(value = "movieId") int movieId,
            @RequestBody ReviewDto reviewDto
    ) {
        return new ResponseEntity<>(reviewService.createReview(movieId, reviewDto), HttpStatus.CREATED);
    }

    @GetMapping("/{movieId}/reviews")
    public ResponseEntity<List<ReviewDto>> getReviewsByMovieId(@PathVariable(value = "movieId") int movieId) {
        return new ResponseEntity<>(reviewService.getReviewsByMovieId(movieId), HttpStatus.OK);
    }

    @GetMapping("/{movieId}/reviews/{reviewId}")
    public ResponseEntity<ReviewDto> getReviewById(
            @PathVariable(name = "movieId") int movieId,
            @PathVariable(name = "reviewId") int reviewId
    ) {
        var reviewDto = reviewService.getReviewById(reviewId, movieId);
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }

    @PutMapping("/{movieId}/reviews/{reviewId}")
    public ResponseEntity<ReviewDto> updateReviewById(
            @PathVariable(name = "movieId") int movieId,
            @PathVariable(name = "reviewId") int reviewId,
            @RequestBody ReviewDto reviewDto
    ) {
        var reviewResponse = reviewService.updateReview(movieId, reviewId, reviewDto);
        return new ResponseEntity<>(reviewResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{movieId}/reviews/{reviewId}")
    public ResponseEntity<String> deleteReviewById(
            @PathVariable(name = "movieId") int movieId,
            @PathVariable(name = "reviewId") int reviewId
    ) {
        reviewService.deleteReview(movieId, reviewId);
        return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
    }
}
