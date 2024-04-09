package com.manish.blog.payloads;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@NoArgsConstructor
@Getter
@Setter

public class UserDto {
    private int id;

    @NotEmpty // check for both null and blank
    @Size(min = 4, message="Username must be more than length of 3 characters ")
    private String name;

    @NotEmpty
    @Email(message = "Email address is not valid!!")
    private String email;

    @NotEmpty
    @Size(min = 3 , max = 10, message = "Password must be min of 3 chars and max of 10 chars !!")
//    @Pattern(regexp = )
    private String password;

    @NotEmpty
    private String about;
}
