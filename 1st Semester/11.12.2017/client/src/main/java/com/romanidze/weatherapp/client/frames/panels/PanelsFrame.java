package com.romanidze.weatherapp.client.frames.panels;

import javax.swing.*;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * 06.12.2017
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public class PanelsFrame {

    private List<JPanel> panelList = new ArrayList<>();

    private void initPanelList(){

        JPanel statusPanel = new JPanel();
        JPanel firstBoxPanel = new JPanel();
        JPanel secondBoxPanel = new JPanel();

        statusPanel.setLayout(new BorderLayout());
        firstBoxPanel.setLayout(new BoxLayout(firstBoxPanel, BoxLayout.Y_AXIS));
        secondBoxPanel.setLayout(new BoxLayout(secondBoxPanel, BoxLayout.Y_AXIS));

        this.panelList.add(statusPanel);
        this.panelList.add(firstBoxPanel);
        this.panelList.add(secondBoxPanel);

    }

    protected List<JPanel> getPanelList() {
        this.initPanelList();
        return this.panelList;
    }
}
