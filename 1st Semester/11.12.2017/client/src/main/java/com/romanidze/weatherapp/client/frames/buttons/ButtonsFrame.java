package com.romanidze.weatherapp.client.frames.buttons;

import javax.swing.JButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 06.12.2017
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public class ButtonsFrame {

    private Map<String, List<JButton> > buttonsMap = new HashMap<>();

    private void initButtons(){

        List<JButton> middleButtonsList = new ArrayList<>();
        middleButtonsList.add(new JButton("Hi first"));
        middleButtonsList.add(new JButton("Hi second"));
        middleButtonsList.add(new JButton("Button 4"));
        this.buttonsMap.put("firstbox", middleButtonsList);

        List<JButton> rightButtonsList = new ArrayList<>();
        rightButtonsList.add(new JButton("Button 1"));
        rightButtonsList.add(new JButton("Button 2"));
        rightButtonsList.add(new JButton("Button 3"));
        this.buttonsMap.put("secondbox", rightButtonsList);

    }

    public Map<String, List<JButton>> getButtonsMap() {
        this.initButtons();
        return this.buttonsMap;
    }
}
