package ru.khamzin.movie.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.khamzin.movie.dto.ReviewDto;
import ru.khamzin.movie.entity.ReviewEntity;
import ru.khamzin.movie.exception.MovieNotFoundException;
import ru.khamzin.movie.exception.ReviewNotFoundException;
import ru.khamzin.movie.repository.MovieRepository;
import ru.khamzin.movie.repository.ReviewRepository;
import ru.khamzin.movie.service.ReviewService;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, MovieRepository movieRepository) {
        this.reviewRepository = reviewRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public ReviewDto createReview(int movieId, ReviewDto reviewDto) {
        var reviewEntity = mapToEntity(reviewDto);

        var movieEntity = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie with review not found"));

        reviewEntity.setMovie(movieEntity);
        var newReview = reviewRepository.save(reviewEntity);
        return mapToDto(newReview);
    }

    @Override
    public List<ReviewDto> getReviewsByMovieId(int movieId) {
        List<ReviewEntity> reviews = reviewRepository.findByMovie_Id(movieId);

        return reviews.stream()
                .map(review -> mapToDto(review))
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDto getReviewById(int reviewId, int movieId) {
        var movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie with associate review not found"));

        var review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review with associate movie not found"));

        if (!review.getMovie().getId().equals(movie.getId())) {
            throw new ReviewNotFoundException("This review does not belong to a movie");
        }

        return mapToDto(review);
    }

    @Override
    public ReviewDto updateReview(int movieId, int reviewId, ReviewDto reviewDto) {
        var movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie with associate review not found"));

        var review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review with associate movie not found"));

        if (!review.getMovie().getId().equals(movie.getId())) {
            throw new ReviewNotFoundException("This review does not belong to a movie");
        }

        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());

        var updatedReview = reviewRepository.save(review);

        return mapToDto(updatedReview);
    }

    @Override
    public void deleteReview(int movieId, int reviewId) {
        var movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie with associate review not found"));

        var review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review with associate movie not found"));

        if (!review.getMovie().getId().equals(movie.getId())) {
            throw new ReviewNotFoundException("This review does not belong to a movie");
        }

        reviewRepository.delete(review);
    }

    private ReviewDto mapToDto(ReviewEntity reviewEntity) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(reviewEntity.getId());
        reviewDto.setTitle(reviewEntity.getTitle());
        reviewDto.setContent(reviewEntity.getContent());
        reviewDto.setStars(reviewEntity.getStars());
        return reviewDto;
    }

    private ReviewEntity mapToEntity(ReviewDto reviewDto) {
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setId(reviewDto.getId());
        reviewEntity.setTitle(reviewDto.getTitle());
        reviewEntity.setContent(reviewDto.getContent());
        reviewEntity.setStars(reviewDto.getStars());
        return reviewEntity;
    }
}
