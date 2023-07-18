package com.joecjw.SpringBootProjectPractice.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestModel {

    @NotBlank
    private String email;

    @NotBlank
    private String oldPassword;

    private String newPassword;
}
