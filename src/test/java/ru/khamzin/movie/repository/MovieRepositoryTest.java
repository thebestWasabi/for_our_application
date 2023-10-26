package ru.khamzin.movie.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.khamzin.movie.entity.MovieEntity;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void MovieRepository_SaveAll_ReturnSavedMovie() {
        //Arrange
        MovieEntity movie = MovieEntity.builder()
                .name("Secret Window")
                .type("art")
                .build();

        //Act
        var savedMovie = movieRepository.save(movie);

        //Assert
        Assertions.assertThat(savedMovie).isNotNull();
        Assertions.assertThat(savedMovie.getId()).isGreaterThan(0);
    }
}
