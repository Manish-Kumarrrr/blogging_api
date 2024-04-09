package com.manish.blog.payloads;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor

public class CategoryDto {

    private Integer categoryId;

    @NotBlank
    @Size(min=4)
    private String categoryTitle;

    @NotBlank
    @Size(max=100)
    private String categoryDescription;
}
