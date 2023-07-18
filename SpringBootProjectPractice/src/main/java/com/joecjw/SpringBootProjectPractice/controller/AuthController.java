package com.joecjw.SpringBootProjectPractice.controller;

import com.joecjw.SpringBootProjectPractice.entity.User;
import com.joecjw.SpringBootProjectPractice.model.LoginRequestModel;
import com.joecjw.SpringBootProjectPractice.entity.registration.VerificationToken;
import com.joecjw.SpringBootProjectPractice.event.RegistrationCompleteEvent;
import com.joecjw.SpringBootProjectPractice.exception.DepartmentNotFoundException;
import com.joecjw.SpringBootProjectPractice.exception.InvalidUserRoleException;
import com.joecjw.SpringBootProjectPractice.exception.UserAlreadyExistException;
import com.joecjw.SpringBootProjectPractice.exception.VerificationTokenNotFoundException;
import com.joecjw.SpringBootProjectPractice.model.EmailModel;
import com.joecjw.SpringBootProjectPractice.model.JwtResponseModel;
import com.joecjw.SpringBootProjectPractice.security.jwt.JwtUtils;
import com.joecjw.SpringBootProjectPractice.security.services.UserDetailsImpl;
import com.joecjw.SpringBootProjectPractice.service.RegistrationService;
import com.joecjw.SpringBootProjectPractice.utils.EmailUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private AuthenticationManager authenticationManager;

    private JwtUtils jwtUtils;

    private RegistrationService registrationService;

    private ApplicationEventPublisher publisher;

    private EmailUtils emailUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestModel loginRequestModel) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestModel.getEmail(),
                        loginRequestModel.getOldPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponseModel(
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles,
                "Bearer",
                jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user,
                                          @RequestParam(name = "teacher_department", required = false) String teacherDepartment,
                                          final HttpServletRequest request) throws UserAlreadyExistException, InvalidUserRoleException, DepartmentNotFoundException {
        User _user = registrationService.registerUser(user, teacherDepartment);
        publisher.publishEvent(new RegistrationCompleteEvent(_user, applicationUrl(request)));
        return new ResponseEntity<>("A Verification Email Has Been Sent To Your Email Address to Activate Your Account.", HttpStatus.CREATED);
    }

    @GetMapping("/register/verifyRegistration")
    public ResponseEntity<?> verifyRegistration(@RequestParam(name = "token") String token){
        String result = registrationService.validateVerificationToken(token);
        if(result.equalsIgnoreCase("valid")){
            return new ResponseEntity<>("User Verified", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Verification Failed", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/register/resendVerificationToken")
    public ResponseEntity<?> resendVerificationToken(@RequestParam("oldToken") String oldToken,
                                                     HttpServletRequest request) throws VerificationTokenNotFoundException {
        VerificationToken verificationToken = registrationService.generateNewVerificationToken(oldToken);
        User user = verificationToken.getUser();
        resendVerificationTokenMail(user, applicationUrl(request), verificationToken);
        return new ResponseEntity<>("Verification Link Sent", HttpStatus.OK);
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://"
                + request.getServerName()
                + ":"
                + request.getServerPort()
                +"/api/auth/register"
                + request.getContextPath();
    }

    private void resendVerificationTokenMail(User user, String applicationUrl, VerificationToken verificationToken) {
        String url = applicationUrl
                + "/verifyRegistration?token="
                + verificationToken.getToken();

        EmailModel emailModel = new EmailModel();
        emailModel.setTo(user.getEmail());
        emailModel.setSubject("SpringBoot Project - User Activation");
        emailModel.setBody("Please click the link to verify your account: " + url);

        emailUtils.send(emailModel);
    }
}
