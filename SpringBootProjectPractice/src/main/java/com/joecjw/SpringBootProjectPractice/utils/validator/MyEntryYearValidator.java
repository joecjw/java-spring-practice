package com.joecjw.SpringBootProjectPractice.utils.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.Year;


public class MyEntryYearValidator implements ConstraintValidator<ValidEntryYear, Integer> {

    @Override
    public void initialize(ValidEntryYear constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null){
            return false;
        }

        int currentYear = Year.now().getValue();
        if(value.intValue() <= currentYear && value.intValue() > 2000){
            return true;
        }
        return false;
    }
}
