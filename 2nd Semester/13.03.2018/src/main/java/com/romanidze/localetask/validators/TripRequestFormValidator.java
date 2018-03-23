package com.romanidze.localetask.validators;

import com.romanidze.localetask.forms.TripRequestForm;
import com.romanidze.localetask.utils.ConstantsClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

/**
 * 23.03.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Component
public class TripRequestFormValidator implements Validator {

    @Autowired
    private ConstantsClass constants;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.getName().equals(TripRequestForm.class.getName());
    }

    @Override
    public void validate(Object target, Errors errors) {

        TripRequestForm form = (TripRequestForm) target;

        try{
            LocalDateTime departureTime = LocalDateTime.parse(form.getDepartureDate(), this.constants.getFormatter());
        }catch(DateTimeParseException e){
            errors.rejectValue("departureDate", "error.departureDate");
        }

        try{
            LocalDateTime arrivalTime = LocalDateTime.parse(form.getArrivalDate(), this.constants.getFormatter());
        }catch(DateTimeParseException e){
            errors.rejectValue("arrivalDate", "error.arrivalDate");
        }

        List<String> fieldsNames = Arrays.asList("country", "departureDate", "arrivalDate");

        fieldsNames.forEach(
                field -> ValidationUtils.rejectIfEmptyOrWhitespace(errors, field, "empty.field")
        );

    }
}
