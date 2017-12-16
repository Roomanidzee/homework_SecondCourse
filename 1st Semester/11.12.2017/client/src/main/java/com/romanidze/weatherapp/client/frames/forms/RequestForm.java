package com.romanidze.weatherapp.client.frames.forms;

import com.romanidze.weatherapp.client.frames.utils.SpringUtilities;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SpringLayout;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.Component;
import java.awt.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 12.12.2017
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public class RequestForm {

    private JPanel mainPanel = new JPanel(new SpringLayout());
    private List<String> labels = Arrays.asList("Страна", "Город", "Количество дней");

    private void initMainPanel(){

        List<JTextField> textFields = IntStream.range(0, this.labels.size())
                                               .mapToObj(i -> new JTextField())
                                               .collect(Collectors.toList());

        AtomicInteger count = new AtomicInteger();

        this.labels.stream()
                   .map(label1 -> new JLabel(label1, JLabel.TRAILING))
                   .forEachOrdered(label -> {

                        int index = count.getAndIncrement();

                        this.mainPanel.add(label);

                        label.setLabelFor(textFields.get(index));
                        this.mainPanel.add(textFields.get(index));

                   }
        );

        JButton saveButton = new JButton("Отправить");

        saveButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                List<Component> components = new ArrayList<>(Arrays.asList(mainPanel.getComponents()));
                List<JTextField> textFields1 = IntStream.range(0, components.size())
                                                        .filter(i -> components.get(i)
                                                                               .getClass()
                                                                               .equals(JTextField.class))
                                                        .mapToObj(i -> (JTextField) components.get(i))
                                                        .collect(Collectors.toList());

                Long emptyCount = textFields1.stream()
                                             .filter(textField -> textField.getText().equals(""))
                                             .count();

                if(emptyCount == textFields.size()){
                    saveButton.setText("Заполните все поля!");
                    saveButton.setForeground(Color.RED);
                }else if(emptyCount > 0 && emptyCount < textFields.size()){
                    saveButton.setText("Заполните пустые поля!");
                    saveButton.setForeground(Color.RED);
                }else{
                    saveButton.setText("Данные заполнены, можно отправлять!");
                    saveButton.setForeground(Color.GREEN);
                }

            }

            @Override
            public void mouseExited(MouseEvent e) {
               saveButton.setText("Отправить");
               saveButton.setForeground(Color.BLACK);
            }

        });

        this.mainPanel.add(saveButton);

        SpringUtilities.makeGrid(this.mainPanel, this.labels.size(),
                                          2, 7, 7, 7, 7);


    }

    public JPanel getMainPanel() {
        this.initMainPanel();
        return this.mainPanel;
    }
}
