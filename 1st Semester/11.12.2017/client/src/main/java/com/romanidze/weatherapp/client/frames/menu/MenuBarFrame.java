package com.romanidze.weatherapp.client.frames.menu;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import java.awt.Font;

/**
 * 06.12.2017
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public class MenuBarFrame {

    private JMenuBar menuBar = new JMenuBar();
    private JFrame frame;

    public MenuBarFrame(JFrame frame){
        this.frame = frame;
    }


    private void initMenu(){

        Font font = new Font("Verdana", Font.PLAIN, 11);

        JMenu fileMenu = new JMenu("File");
        JMenu aboutMenu = new JMenu("About");
        aboutMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(frame, "WeatherApp, version 2.0");
            }
        });

        JMenuItem exitItem = new JMenuItem("Exit");

        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.setFont(font);
        aboutMenu.setFont(font);
        exitItem.setFont(font);

        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        this.menuBar.add(fileMenu);
        this.menuBar.add(aboutMenu);

    }

    public JMenuBar getMenuBar() {
        this.initMenu();
        return this.menuBar;
    }
}
