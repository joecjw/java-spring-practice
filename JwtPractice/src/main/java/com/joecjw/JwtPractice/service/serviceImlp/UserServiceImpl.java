package com.joecjw.JwtPractice.service.serviceImlp;

import com.joecjw.JwtPractice.entity.User;
import com.joecjw.JwtPractice.repository.UserRepository;
import com.joecjw.JwtPractice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public ArrayList<User> getAllUsers() {
        ArrayList<User> userList = new ArrayList<>();
        userRepository.findAll().forEach(userList::add);
        return userList;
    }
}
