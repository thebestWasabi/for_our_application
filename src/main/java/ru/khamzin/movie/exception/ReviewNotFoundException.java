package ru.khamzin.movie.exception;

import java.io.Serial;

public class ReviewNotFoundException extends RuntimeException {

    @Serial
    private final static long serialVersionUID = 2;

    public ReviewNotFoundException(String message) {
        super(message);
    }
}
