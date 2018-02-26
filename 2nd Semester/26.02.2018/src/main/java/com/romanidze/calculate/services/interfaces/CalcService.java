package com.romanidze.calculate.services.interfaces;

import com.romanidze.calculate.forms.CalcAction;

/**
 * 26.02.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface CalcService {

    Double getCalcResult(CalcAction calcForm);

}
