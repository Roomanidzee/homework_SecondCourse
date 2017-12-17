package panels;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Color;

/**
 * 16.12.2017
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public class CircleDraw {

    private JPanel panel = new JPanel(){

        @Override
        public void paintComponent(Graphics g){

            g.setColor(Color.BLACK);
            g.fillOval(50, 50, 50, 50);

        }

    };


    public JPanel getPanel() {
        return this.panel;
    }

}
