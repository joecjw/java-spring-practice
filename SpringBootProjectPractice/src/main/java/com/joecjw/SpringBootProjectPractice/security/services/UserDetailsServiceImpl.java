package com.joecjw.SpringBootProjectPractice.security.services;

import com.joecjw.SpringBootProjectPractice.entity.User;
import com.joecjw.SpringBootProjectPractice.exception.UserEmailNotFoundException;
import com.joecjw.SpringBootProjectPractice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UserEmailNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()) {
            if(user.get().isActive()){
                return UserDetailsImpl.build(user.get());
            }else {
                throw new UserEmailNotFoundException("User with email: " +email+ " Not Activated Yet");
            }
        } else {
            throw new UserEmailNotFoundException("User with email: " +email+ " Not Found");
        }
    }

}