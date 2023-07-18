package com.joecjw.SpringBootProjectPractice.controller;


import com.joecjw.SpringBootProjectPractice.model.EmailModel;
import com.joecjw.SpringBootProjectPractice.service.EmailService;
import com.joecjw.SpringBootProjectPractice.utils.validator.EmailModelValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
@AllArgsConstructor
public class EmailController {

    private EmailService emailService;

    private EmailModelValidator emailModelValidator;

    @PostMapping("/mail/send")
    public ResponseEntity<?> sendEmail(@ModelAttribute EmailModel emailModel){

        String validationResult = emailModelValidator.validate(emailModel);
        if(emailModelValidator.validate(emailModel) == "valid"){
            String sendResult = emailService.sendEmail(emailModel);
            if(sendResult.contains("Error")){
                return new ResponseEntity<>("Send Email Failed:" + sendResult ,HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(sendResult, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(validationResult, HttpStatus.BAD_REQUEST);
        }
    }

}
