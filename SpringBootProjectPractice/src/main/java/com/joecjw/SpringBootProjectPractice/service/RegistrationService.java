package com.joecjw.SpringBootProjectPractice.service;

import com.joecjw.SpringBootProjectPractice.entity.User;
import com.joecjw.SpringBootProjectPractice.entity.registration.VerificationToken;
import com.joecjw.SpringBootProjectPractice.exception.*;

public interface RegistrationService {

    User registerUser(User user, String teacherDepartment) throws UserAlreadyExistException, InvalidUserRoleException, DepartmentNotFoundException;

    void saveVerificationTokenForUser(String token, User user);

    String validateVerificationToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken) throws VerificationTokenNotFoundException;

}
