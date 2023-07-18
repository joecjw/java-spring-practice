package com.joecjw.JwtPractice.security.service;

import com.joecjw.JwtPractice.exception.UsernameEmailNotFoundException;
import com.joecjw.JwtPractice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userNameEmail) throws UsernameEmailNotFoundException {
        String[] index = userNameEmail.split(":");
        String firstName = index[0].split(" ")[0].trim();
        String lastName = index[0].split(" ")[1].trim();
        String email = index[1].trim();

        return userRepository.findAllByFirstNameAndLastNameAndEmail(firstName, lastName, email)
                .orElseThrow(() -> new UsernameEmailNotFoundException(
                        "User with Username= " + firstName + " " + lastName +
                        " And Email= " + email +" Not Found")
                );

    }
}
