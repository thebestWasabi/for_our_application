package ru.khamzin.movie.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {

    private Integer id;
    private String title;
    private String content;
    private int stars;
}
