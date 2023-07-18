package com.joecjw.SpringBootProjectPractice.service;

import com.joecjw.SpringBootProjectPractice.entity.User;
import com.joecjw.SpringBootProjectPractice.entity.registration.PasswordResetToken;
import com.joecjw.SpringBootProjectPractice.exception.PasswordResetTokenNotFoundException;
import com.joecjw.SpringBootProjectPractice.exception.UserNotFoundException;

public interface UserPasswordService {
    PasswordResetToken createPasswordResetTokenForUser(User user, String token);

    String validatePasswordResetToken(String token);

    User getUserByPasswordResetToken(String token) throws PasswordResetTokenNotFoundException, UserNotFoundException;

    void changePassword(User user, String newPassword);

    boolean checkValidOldPassword(User user, String oldPassword);

    User findUserByEmail(String email) throws UserNotFoundException ;
}
