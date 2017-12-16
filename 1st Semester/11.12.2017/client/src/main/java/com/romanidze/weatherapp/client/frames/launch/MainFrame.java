package com.romanidze.weatherapp.client.frames.launch;

import com.romanidze.weatherapp.client.frames.customtheme.CustomTheme;
import com.romanidze.weatherapp.client.frames.menu.MenuBarFrame;
import com.romanidze.weatherapp.client.frames.panels.PanelsInitFrame;

import javax.swing.UIManager;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Dimension;

/**
 * 06.12.2017
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public class MainFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::launchWindow);
    }

    private static void launchWindow(){

        JFrame frame = new JFrame("WeatherApp Client");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        try {
            UIManager.setLookAndFeel(CustomTheme.class.getCanonicalName());
        } catch (ClassNotFoundException | InstantiationException |
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        MenuBarFrame menuBarFrame = new MenuBarFrame(frame);
        PanelsInitFrame panelsInitFrame = new PanelsInitFrame();

        frame.setJMenuBar(menuBarFrame.getMenuBar());
        frame.setContentPane(panelsInitFrame.getMainPanel());

        frame.setPreferredSize(new Dimension(500, 500));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

}
