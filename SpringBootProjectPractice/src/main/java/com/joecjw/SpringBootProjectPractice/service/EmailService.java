package com.joecjw.SpringBootProjectPractice.service;

import com.joecjw.SpringBootProjectPractice.model.EmailModel;

public interface EmailService {

    public String sendEmail(EmailModel emailModel);
}
