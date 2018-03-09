package com.romanidze.formvalidatetask.validators;

import com.romanidze.formvalidatetask.forms.TripRequestForm;
import com.romanidze.formvalidatetask.utils.ConstantsClass;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

/**
 * 09.03.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Component
public class TripRequestFormValidator implements Validator{

    private ConstantsClass constants = new ConstantsClass();

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
            errors.rejectValue("departureDate", "error.departureDate", "Неправильно введена дата отправления");
        }

        try{
            LocalDateTime arrivalTime = LocalDateTime.parse(form.getArrivalDate(), this.constants.getFormatter());
        }catch(DateTimeParseException e){
            errors.rejectValue("arrivalDate", "error.arrivalDate", "Неправильно введена дата прибытия");
        }

        List<String> fieldsNames = Arrays.asList("country", "departureDate", "arrivalDate");

        fieldsNames.forEach(field ->
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, field, "empty.field", "Незаполненное поле"));

    }
}
