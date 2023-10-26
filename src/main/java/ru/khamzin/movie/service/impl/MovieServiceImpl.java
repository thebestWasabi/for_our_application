package ru.khamzin.movie.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.khamzin.movie.dto.MovieDto;
import ru.khamzin.movie.dto.MovieResponse;
import ru.khamzin.movie.entity.MovieEntity;
import ru.khamzin.movie.exception.MovieNotFoundException;
import ru.khamzin.movie.repository.MovieRepository;
import ru.khamzin.movie.service.MoviesService;

import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MoviesService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public MovieResponse getAllMovie(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        var movies = movieRepository.findAll(pageable);
        var listOfMovies = movies.getContent();

        var content = listOfMovies.stream()
                .map(movie -> mapToDto(movie))
                .collect(Collectors.toList());

        MovieResponse movieResponse = new MovieResponse();
        movieResponse.setContent(content);
        movieResponse.setPageNumber(movies.getNumber());
        movieResponse.setPageSize(movies.getSize());
        movieResponse.setTotalElements(movies.getTotalElements());
        movieResponse.setTotalPages(movies.getTotalPages());
        movieResponse.setLast(movies.isLast());

        return movieResponse;
    }

    @Override
    public MovieDto getMovieById(Integer id) {
        var movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie could not be found"));
        return mapToDto(movie);
    }

    @Override
    public MovieDto createMovie(MovieDto movieDto) {
        MovieEntity movie = new MovieEntity();
        movie.setName(movieDto.getName());
        movie.setType(movieDto.getType());

        MovieEntity createdMovie = movieRepository.save(movie);

        MovieDto movieResponse = new MovieDto();
        movieResponse.setId(createdMovie.getId());
        movieResponse.setName(createdMovie.getName());
        movieResponse.setType(createdMovie.getType());

        return movieResponse;
    }

    @Override
    public MovieDto updateMovie(MovieDto movieDto, Integer id) {
        var movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie could not be update"));

        movie.setName(movieDto.getName());
        movie.setType(movieDto.getType());

        var updatedMovie = movieRepository.save(movie);

        return mapToDto(updatedMovie);
    }

    @Override
    public void deleteMovie(Integer id) {
        var movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie could not be found"));

        movieRepository.delete(movie);
    }

    private MovieDto mapToDto(MovieEntity movieEntity) {
        MovieDto movieDto = new MovieDto();
        movieDto.setId(movieEntity.getId());
        movieDto.setName(movieEntity.getName());
        movieDto.setType(movieEntity.getType());
        return movieDto;
    }

    private MovieEntity mapToEntity(MovieDto movieDto) {
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setName(movieDto.getName());
        movieEntity.setType(movieDto.getType());

        return movieEntity;
    }
}
