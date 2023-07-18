package com.joecjw.SpringBootProjectPractice.utils.validator;

import com.joecjw.SpringBootProjectPractice.model.EmailModel;
import org.springframework.stereotype.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EmailModelValidator {

    private Pattern pattern;
    private Matcher matcher;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";

    public String validate(EmailModel emailModel) {

        //validate email address
        if (emailModel.getSENDER_EMAIL() == null){
            return "Sender Email Address Not Set Internally";
        }else if(emailModel.getTo() == null) {
            return "Empty Destination Email Address";
        }else {
           if(!isValidEmailAddress(emailModel.getSENDER_EMAIL())){
               return "Invalid Sender Email Address";
           }else if(!isValidEmailAddress(emailModel.getTo())){
               return "Invalid Destination Email Address";
           }else {
               if(emailModel.getCc() != null){
                   for (String copyEmailAddress:emailModel.getCc()) {
                       if(!isValidEmailAddress(copyEmailAddress)){
                           return "Invalid Copy Email Address: "+
                                   copyEmailAddress;
                       }
                   }
               }
           }
        }

        //validate other fields
        if(emailModel.getSubject() == null
                || emailModel.getSubject().isBlank()){
            return "Empty Subject";
        }

        if(emailModel.getBody() == null
                || emailModel.getBody().isBlank()){
            return "Empty Body";
        }

        return "valid";

    }

    public boolean isValidEmailAddress(String emailAddress){
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(emailAddress);
        if(!matcher.matches()) {
            return false;
        }
        return true;
    }
}


