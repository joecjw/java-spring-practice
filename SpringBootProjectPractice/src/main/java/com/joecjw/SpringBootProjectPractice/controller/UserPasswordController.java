package com.joecjw.SpringBootProjectPractice.controller;

import com.joecjw.SpringBootProjectPractice.entity.User;
import com.joecjw.SpringBootProjectPractice.entity.registration.PasswordResetToken;
import com.joecjw.SpringBootProjectPractice.exception.PasswordResetTokenNotFoundException;
import com.joecjw.SpringBootProjectPractice.exception.UserNotFoundException;
import com.joecjw.SpringBootProjectPractice.model.EmailModel;
import com.joecjw.SpringBootProjectPractice.model.LoginRequestModel;
import com.joecjw.SpringBootProjectPractice.service.UserPasswordService;
import com.joecjw.SpringBootProjectPractice.utils.EmailUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UserPasswordController {

    private UserPasswordService userPasswordService;

    private EmailUtils emailUtils;

    @PostMapping("/users/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody LoginRequestModel loginRequestModel,
                                           HttpServletRequest request) throws UserNotFoundException {
        User user = userPasswordService.findUserByEmail(loginRequestModel.getEmail());
        String url = "";
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = userPasswordService.createPasswordResetTokenForUser(user, token);
        url = passwordResetTokenMail(user, applicationUrl(request), token);
        return new ResponseEntity<>(url, HttpStatus.OK);
    }

    @PostMapping("/users/saveNewPassword")
    public ResponseEntity<?> savePassword(@RequestParam("token") String token,
                                          @RequestBody LoginRequestModel loginRequestModel) throws UserNotFoundException, PasswordResetTokenNotFoundException {
        String result = userPasswordService.validatePasswordResetToken(token);
        if(result.equalsIgnoreCase("valid")){
            User user = userPasswordService.getUserByPasswordResetToken(token);
            userPasswordService.changePassword(user,loginRequestModel.getNewPassword());
            return new ResponseEntity<>("Reset Password Successfully", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Reset Password Failed due to Invalid Token", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/users/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody LoginRequestModel loginRequestModel) throws UserNotFoundException {
        User user = userPasswordService.findUserByEmail(loginRequestModel.getEmail());
        if(!userPasswordService.checkValidOldPassword(user, loginRequestModel.getOldPassword())){
            return new ResponseEntity<>("Password Changed Failed due to Invalid Old Password",HttpStatus.FORBIDDEN);
        }else {
            userPasswordService.changePassword(user, loginRequestModel.getNewPassword());
            return new ResponseEntity<>("Password Changed Successfully" ,HttpStatus.OK);
        }
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://"
                + request.getServerName()
                + ":"
                + request.getServerPort()
                +"/api/users"
                + request.getContextPath();
    }

    private String passwordResetTokenMail(User user, String applicationUrl, String token) {
        String url = applicationUrl
                + "/saveNewPassword?token="
                + token;

        EmailModel emailModel = new EmailModel();
        emailModel.setTo(user.getEmail());
        emailModel.setSubject("SpringBoot Project - User Password Reset");
        emailModel.setBody("Please click the link to reset your password: " + url);

        emailUtils.send(emailModel);
        return url;
    }

}
