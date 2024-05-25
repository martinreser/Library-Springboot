package com.library.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthorDto {
    private String name;
    private String lastName;
    private String dateOfBirth;
    private String nationality;
}
