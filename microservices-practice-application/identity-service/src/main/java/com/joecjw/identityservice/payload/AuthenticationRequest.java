package com.joecjw.identityservice.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationRequest {

    //format [firstName lastName:email]
    @NotBlank(message = "The user's name and email can not be blank")
    private String userNameEmail;

    @NotBlank(message = "The user's password can not be blank")
    private String password;

}
