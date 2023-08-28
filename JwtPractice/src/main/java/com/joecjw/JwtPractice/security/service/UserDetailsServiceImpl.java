package com.joecjw.JwtPractice.security.service;

import com.joecjw.JwtPractice.exception.InvalidUsernameException;
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
    public UserDetails loadUserByUsername(String userNameEmail) throws UsernameEmailNotFoundException, InvalidUsernameException {
        String[] index = userNameEmail.split(":");
        String[] name = index[0].split(" ");
        if(name.length != 2){
            throw new InvalidUsernameException("Invalid Username Format");
        }
        String firstName = name[0].trim();
        String lastName = name[1].trim();
        String email = index[1].trim();

        return userRepository.findAllByFirstNameAndLastNameAndEmail(firstName, lastName, email)
                .orElseThrow(() -> new UsernameEmailNotFoundException(
                        "User with Username= " + firstName + " " + lastName +
                        " And Email= " + email +" Not Found")
                );

    }
}
