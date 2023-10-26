package ru.khamzin.movie.exception;

import java.io.Serial;

public class MovieNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1;

    public MovieNotFoundException(String message) {
        super(message);
    }
}
