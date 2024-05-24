package com.library.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookDto {

    @NotBlank(message = "Name cannot be blank")
    @JsonProperty("name")
    private String name;

    @Digits(integer = 10, fraction = 0, message = "Invalid id format")
    @JsonProperty("authorId")
    private int authorId;

    @JsonProperty("yearRelease")
    private String yearRelease;
}
