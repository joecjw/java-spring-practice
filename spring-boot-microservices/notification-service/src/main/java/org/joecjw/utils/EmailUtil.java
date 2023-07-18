package org.joecjw.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.joecjw.dto.NotificationEmailDto;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
@AllArgsConstructor
public class EmailUtil {

    private JavaMailSender javaMailSender;

    public String send(NotificationEmailDto notificationEmailDto){
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(notificationEmailDto.getSENDER_EMAIL());
            mimeMessageHelper.setTo(notificationEmailDto.getTo());
            mimeMessageHelper.setSubject(notificationEmailDto.getSubject());
            if(notificationEmailDto.getCc() != null){
                mimeMessageHelper.setCc(notificationEmailDto.getCc());

            }
            mimeMessageHelper.setSentDate(new Date());
            mimeMessageHelper.setText(notificationEmailDto.getBody());
            if(notificationEmailDto.getClassPathResourceName() != null){
                mimeMessageHelper.addAttachment(notificationEmailDto.getClassPathResourceName(),
                        new ClassPathResource(notificationEmailDto.getClassPathResourceName()));
            }

            javaMailSender.send(mimeMessage);
            return "Mail Send Successfully";

        }catch (MessagingException e){
            return "Error Occur During Messaging Operation";
        } catch (RuntimeException e){
            return "Unexpected Error Occur When Sending the Email"+e.getMessage();
        }
    }
}