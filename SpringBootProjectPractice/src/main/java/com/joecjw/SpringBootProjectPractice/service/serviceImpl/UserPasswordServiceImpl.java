package com.joecjw.SpringBootProjectPractice.service.serviceImpl;

import com.joecjw.SpringBootProjectPractice.entity.User;
import com.joecjw.SpringBootProjectPractice.entity.registration.PasswordResetToken;
import com.joecjw.SpringBootProjectPractice.exception.PasswordResetTokenNotFoundException;
import com.joecjw.SpringBootProjectPractice.exception.UserNotFoundException;
import com.joecjw.SpringBootProjectPractice.repository.*;
import com.joecjw.SpringBootProjectPractice.service.UserPasswordService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Calendar;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserPasswordServiceImpl implements UserPasswordService {

    private UserRepository userRepository;


    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public PasswordResetToken createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(user, token);
        passwordResetTokenRepository.save(passwordResetToken);
        return passwordResetToken;
    }

    @Override
    public String validatePasswordResetToken(String token) {
        Optional<PasswordResetToken> passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if(passwordResetToken.isPresent()){
            User user = passwordResetToken.get().getUser();
            Calendar calendar = Calendar.getInstance();
            if(passwordResetToken.get().getExpirationTime().getTime()
                    - calendar.getTime().getTime() <= 0){
                passwordResetTokenRepository.delete(passwordResetToken.get());
                return "expired";
            }else {
                return "valid";
            }
        }else{
            return "invalid";
        }
    }

    @Override
    public User getUserByPasswordResetToken(String token) throws PasswordResetTokenNotFoundException, UserNotFoundException {
        Optional<PasswordResetToken> passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if(passwordResetToken.isPresent()){
            User _user = passwordResetToken.get().getUser();
            if(_user != null){
                return _user;
            }else {
                throw new  UserNotFoundException("User For Password Reset Not Found");
            }
        }else {
            throw new PasswordResetTokenNotFoundException("PasswordResetToken entity with token="+token+" Not Found");
        }
    }

    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public boolean checkValidOldPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    @Override
    public User findUserByEmail(String email) throws UserNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            return user.get();
        }else {
            throw new UserNotFoundException("User entity with email="+email+" Not Found");
        }
    }
}
