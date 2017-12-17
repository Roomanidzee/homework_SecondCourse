package buttons;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.Image;
import java.awt.BorderLayout;

import java.io.File;

/**
 * 16.12.2017
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public class UploadButton {

    private JPanel panel;
    private JButton imageButton = new JButton();

    public UploadButton(JPanel panel){
        this.panel = panel;
    }

    //вынести в MainFrame

    private void initButton(){

        this.imageButton.addActionListener(e -> {

            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showDialog(null, "Загрузить картинку");

            if(result == JFileChooser.APPROVE_OPTION){

                File file = fileChooser.getSelectedFile();
                String imagePath = file.getAbsolutePath();

                ImageIcon tempStorage = new ImageIcon(imagePath);
                Image resizedImage = tempStorage.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);

                JLabel imageLabel = new JLabel("", new ImageIcon(resizedImage), JLabel.CENTER);
                this.panel.add(imageLabel, BorderLayout.CENTER);
                this.panel.revalidate();
                this.panel.repaint();

            }

        });

    }

    public JButton getImageButton() {
        this.initButton();
        return this.imageButton;
    }
}
