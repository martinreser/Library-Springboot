package com.book.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookDto {

    @JsonProperty("name")
    private String name;

    @JsonProperty("authorId")
    private int authorId;

    @JsonProperty("yearRelease")
    private String yearRelease;
}
