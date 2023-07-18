package com.joecjw.SpringBootProjectPractice.service.serviceImpl;

import com.joecjw.SpringBootProjectPractice.model.EmailModel;
import com.joecjw.SpringBootProjectPractice.service.EmailService;
import com.joecjw.SpringBootProjectPractice.utils.EmailUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private EmailUtils emailUtils;

    @Override
    public String sendEmail(EmailModel emailModel) {
        return emailUtils.send(emailModel);
    }
}
