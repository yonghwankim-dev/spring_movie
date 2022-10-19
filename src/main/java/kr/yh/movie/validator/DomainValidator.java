package kr.yh.movie.validator;


import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;


public class DomainValidator {

    private DomainValidator(){

    }

    public static boolean validate(Errors errors, Model model){
        if(errors.hasErrors()){
            Map<String, String> validatorResult = validateHandling(errors);
            validatorResult.keySet().stream().forEach(key-> model.addAttribute(key, validatorResult.get(key)));
            return true;
        }
        return false;
    }

    private static Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();
        for(FieldError error : errors.getFieldErrors()){
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }
}
