package com.joecjw.SpringBootProjectPractice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponseModel {

    private Long id;

    private String username;

    private String email;

    private List<String> roles;

    private String type;

    private String token;

}
