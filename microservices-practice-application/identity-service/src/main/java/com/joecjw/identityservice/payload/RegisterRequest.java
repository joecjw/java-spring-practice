package com.joecjw.identityservice.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {

    @NotBlank(message = "The user's first name can not be blank")
    private String firstName;

    @NotBlank(message = "The user's last name can not be blank")
    private String lastName;

    @NotBlank(message = "The user's password can not be blank")
    private String password;

    @Email(message = "Invalid Email")
    private String email;

    private String role;

}