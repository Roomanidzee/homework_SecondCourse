package com.romanidze.weatherapp.client.frames.customtheme;

import javax.swing.plaf.basic.BasicLookAndFeel;
import javax.swing.UIDefaults;

/**
 * 16.12.2017
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public class CustomTheme extends BasicLookAndFeel{

    @Override
    public String getName() {
        return "CustomTheme";
    }

    @Override
    public String getID() {
        return getName();
    }

    @Override
    public String getDescription() {
        return "Кросс-платформенная тема для приложения на Swing";
    }

    @Override
    public boolean isNativeLookAndFeel() {
        return false;
    }

    @Override
    public boolean isSupportedLookAndFeel() {
        return true;
    }

    @Override
    protected void initClassDefaults(UIDefaults table){
        super.initClassDefaults(table);

        table.put("ButtonUI", CustomButton.class.getCanonicalName());
    }

}
