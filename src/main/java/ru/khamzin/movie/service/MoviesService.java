package ru.khamzin.movie.service;

import org.springframework.stereotype.Service;
import ru.khamzin.movie.dto.MovieDto;
import ru.khamzin.movie.dto.MovieResponse;

@Service
public interface MoviesService {

    MovieResponse getAllMovie(int pageNumber, int pageSize);

    MovieDto getMovieById(Integer id);

    MovieDto createMovie(MovieDto movieDto);

    MovieDto updateMovie(MovieDto movieDto, Integer id);

    void deleteMovie(Integer id);
}
