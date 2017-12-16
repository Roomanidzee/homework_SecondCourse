package com.romanidze.weatherapp.client.frames.panels;

import com.romanidze.weatherapp.client.frames.buttons.ButtonsFrame;
import com.romanidze.weatherapp.client.frames.forms.RequestForm;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * 08.12.2017
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public class PanelsInitFrame {

    private JPanel mainPanel = new JPanel();

    private void initPanels(){

        List<String> positions = Arrays.asList(BorderLayout.WEST, BorderLayout.EAST);

        AtomicInteger count = new AtomicInteger();
        this.mainPanel.setLayout(new BorderLayout());

        PanelsFrame panelsFrame = new PanelsFrame();
        List<JPanel> panelList = new ArrayList<>();
        panelList.addAll(panelsFrame.getPanelList());

        ButtonsFrame buttonsFrame = new ButtonsFrame();
        Map<String, List<JButton> > buttonsMap = new HashMap<>();
        buttonsMap.putAll(buttonsFrame.getButtonsMap());

        JLabel status = new JLabel("Status", SwingConstants.CENTER);
        panelList.get(0).add(status);
        this.mainPanel.add(panelList.get(0), BorderLayout.SOUTH);

        panelList.remove(0);

        List<JButton> buttonsFirst = buttonsMap.get("secondbox");

        buttonsFirst.forEach(jButton ->
                jButton.addMouseListener(new MouseAdapter() {

                     @Override
                     public void mouseEntered(MouseEvent e) {
                         status.setText("Наведена мышь");
                     }

                     @Override
                     public void mouseExited(MouseEvent e) {
                          status.setText("Status");
                     }

                }
        ));
        buttonsMap.put("secondbox", buttonsFirst);

        buttonsMap.forEach((key, buttons) -> {

            int index = count.getAndIncrement();

            IntStream.range(0, buttons.size()).forEachOrdered(i -> {
                panelList.get(index).add(buttons.get(i));
                this.mainPanel.add(panelList.get(index), positions.get(index));
            });

        });

        JButton formButton = new JButton("RequestForm");
        formButton.setSize(10, 10);

        formButton.addActionListener(e -> {
            JFrame requestFrame = new JFrame("RequestFrame");
            requestFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            RequestForm requestForm = new RequestForm();
            JPanel requestPanel = requestForm.getMainPanel();
            requestFrame.setContentPane(requestPanel);
            requestFrame.setPreferredSize(new Dimension(700, 700));
            requestFrame.pack();
            requestFrame.setLocationRelativeTo(null);
            requestFrame.setVisible(true);

        });

        this.mainPanel.add(formButton, BorderLayout.CENTER);

    }

    public JPanel getMainPanel() {
        this.initPanels();
        return this.mainPanel;
    }
}
