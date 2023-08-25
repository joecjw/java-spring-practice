package com.joecjw.identityservice.config.service;

import com.joecjw.identityservice.dto.UserDto;
import com.joecjw.identityservice.entity.User;
import com.joecjw.identityservice.exception.UsernameEmailNotFoundException;
import com.joecjw.identityservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userNameEmail) throws UsernameEmailNotFoundException {
        String[] index = userNameEmail.split(":");
        String firstName = index[0].split(" ")[0].trim();
        String lastName = index[0].split(" ")[1].trim();
        String email = index[1].trim();

        User user = userRepository.findAllByFirstNameAndLastNameAndEmail(firstName, lastName, email)
                .orElseThrow(() -> new UsernameEmailNotFoundException(
                        "User with Username= " + firstName + " " + lastName +
                                " And Email= " + email +" Not Found")
                );

        return  UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .isEnabled(user.isEnabled())
                .role(user.getRole())
                .build();
    }
}