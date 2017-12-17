package buttons;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Component;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.Graphics2D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 16.12.2017
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public class MirroringButton {

    private JPanel panel;
    private Image image;
    private BufferedImage bufferedImage;

    private JButton mirroringButton = new JButton();

    public MirroringButton(Image image, JPanel panel){
        this.panel = panel;
        this.image = image;
        this.bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
                                               BufferedImage.TYPE_INT_RGB);
    }

    private void initButton(){

        this.mirroringButton.addActionListener(e -> {

            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-this.image.getWidth(null), 0);

            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            this.bufferedImage = op.filter(this.bufferedImage, null);

            Graphics2D g2d = (Graphics2D) this.panel.getGraphics();

            List<Component> components = new ArrayList<>(Arrays.asList(this.panel.getComponents()));

            Optional<JLabel> imageLabel = components.stream()
                                                    .filter(component -> component.getClass().equals(JLabel.class))
                                                    .map(component -> (JLabel) component)
                                                    .findFirst();

            imageLabel.ifPresent(newImageLabel ->{
                int x = newImageLabel.getX();
                int y = newImageLabel.getY();
                g2d.drawImage(this.bufferedImage, null, x, y);
            });

        });

    }

    public JButton getMirroringButton() {
        this.initButton();
        return this.mirroringButton;
    }
}
