package com.joecjw.SpringBootProjectPractice.event.listener;

import com.joecjw.SpringBootProjectPractice.entity.User;
import com.joecjw.SpringBootProjectPractice.event.RegistrationCompleteEvent;
import com.joecjw.SpringBootProjectPractice.model.EmailModel;
import com.joecjw.SpringBootProjectPractice.service.RegistrationService;
import com.joecjw.SpringBootProjectPractice.utils.EmailUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private EmailUtils emailUtils;

    private RegistrationService registrationService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //Create the Verification Token for the User with Link
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        registrationService.saveVerificationTokenForUser(token, user);

        //Send Mail to user
        String url = event.getApplicationUrl()
                + "/verifyRegistration?token="
                + token;

        EmailModel emailModel = new EmailModel();
        emailModel.setTo(user.getEmail());
        emailModel.setSubject("SpringBoot Project - User Activation");
        emailModel.setBody("Please click the link to verify your account: " + url);

        emailUtils.send(emailModel);
    }
}
