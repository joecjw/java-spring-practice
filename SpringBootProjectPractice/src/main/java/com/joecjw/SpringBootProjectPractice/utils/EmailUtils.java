package com.joecjw.SpringBootProjectPractice.utils;

import com.joecjw.SpringBootProjectPractice.model.EmailModel;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Date;

@Component
@AllArgsConstructor
public class EmailUtils {

    private JavaMailSender javaMailSender;

    public String send(EmailModel emailModel){
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(emailModel.getSENDER_EMAIL());
            mimeMessageHelper.setTo(emailModel.getTo());
            mimeMessageHelper.setSubject(emailModel.getSubject());
            if(emailModel.getCc() != null){
                mimeMessageHelper.setCc(emailModel.getCc());

            }
            mimeMessageHelper.setSentDate(new Date());
            mimeMessageHelper.setText(emailModel.getBody());
            if(emailModel.getFile() != null){
                for (MultipartFile file : emailModel.getFile()) {
                    mimeMessageHelper.addAttachment(file.getOriginalFilename(),
                            new ByteArrayResource(file.getBytes()));
                }
            }

            javaMailSender.send(mimeMessage);
            return "Mail Send Successfully";

        }catch (MessagingException e){
            return "Error Occur During Messaging Operation";
        }catch (IOException e){
            return "Error Occur During IO Operation";
        }catch (RuntimeException e){
            return "Unexpected Error Occur When Sending the Email"+e.getMessage();
        }
    }
}
