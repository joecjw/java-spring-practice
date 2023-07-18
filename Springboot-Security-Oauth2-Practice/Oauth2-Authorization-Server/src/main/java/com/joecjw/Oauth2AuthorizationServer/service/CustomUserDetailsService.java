package com.joecjw.Oauth2AuthorizationServer.service;

import com.joecjw.Oauth2AuthorizationServer.exception.UserEmailNotFoundException;
import com.joecjw.Oauth2AuthorizationServer.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UserEmailNotFoundException {
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserEmailNotFoundException(
                        "User with Email: " + userEmail + " is Not Found"));
    }
}
