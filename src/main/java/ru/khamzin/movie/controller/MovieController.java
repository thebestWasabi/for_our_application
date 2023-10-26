package ru.khamzin.movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.khamzin.movie.dto.MovieDto;
import ru.khamzin.movie.dto.MovieResponse;
import ru.khamzin.movie.service.MoviesService;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MoviesService moviesService;

    @Autowired
    public MovieController(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @GetMapping
    public ResponseEntity<MovieResponse> getAllMovies(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return new ResponseEntity<>(moviesService.getAllMovie(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable("id") Integer movieId) {
        var movieResponse = moviesService.getMovieById(movieId);
        return new ResponseEntity<>(movieResponse, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MovieDto> createMovie(@RequestBody MovieDto movieDto) {
        var movieResponse = moviesService.createMovie(movieDto);
        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> updateMovie(@PathVariable("id") Integer movieId,
                                                @RequestBody MovieDto movieDto) {
        var responseMovie = moviesService.updateMovie(movieDto, movieId);
        return new ResponseEntity<>(responseMovie, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable("id") Integer movieId) {
        moviesService.deleteMovie(movieId);
        return new ResponseEntity<>("Movie deleted successfully", HttpStatus.OK);
    }
}
