package com.romanidze.jade4jtask.services.implementations;

import com.romanidze.jade4jtask.forms.CalcAction;
import com.romanidze.jade4jtask.services.interfaces.CalcService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 05.03.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Service
public class CalcServiceImpl implements CalcService {

    @Override
    public Double getCalcResult(CalcAction calcForm) {

        Double result = null;

        switch(calcForm.getMathAction()){

            case "plus":

                result = calcForm.getFirstNumber() + calcForm.getSecondNumber();
                break;

            case "minus":

                result = calcForm.getFirstNumber() - calcForm.getSecondNumber();
                break;

            case "multiply":

                result = calcForm.getFirstNumber() * calcForm.getSecondNumber();
                break;

            case "divide":

                if(calcForm.getSecondNumber() == 0){
                    throw new IllegalArgumentException("На ноль делить нельзя!");
                }

                result = calcForm.getFirstNumber() / calcForm.getSecondNumber();
                break;

        }

        BigDecimal bd = new BigDecimal(result);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();

    }
}