package buttons;

import java.awt.image.BufferedImage;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.Transparency;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JButton;

/**
 * 16.12.2017
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public class RotateButtons {

    private JButton rightTurnButton = new JButton();
    private JButton leftTurnButton = new JButton();
    private BufferedImage bufferedImage;

    public RotateButtons(Image image){
        this.bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
                                               BufferedImage.TYPE_INT_RGB);
        this.initButtons();
    }

    private GraphicsConfiguration getDefaultConfiguration(){

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        return gd.getDefaultConfiguration();

    }

    private BufferedImage rotateImage(BufferedImage image, double angle){

        double radiansAngle = Math.toRadians(angle);

        double sin = Math.sin(radiansAngle);
        double cos = Math.cos(radiansAngle);

        int width = image.getWidth();
        int height = image.getHeight();

        int newWidth = (int) Math.floor(width * cos + height * sin);
        int newHeight = (int) Math.floor(height * cos + width * sin);

        GraphicsConfiguration gc = this.getDefaultConfiguration();

        BufferedImage result = gc.createCompatibleImage(newWidth, newHeight, Transparency.TRANSLUCENT);
        Graphics2D g = result.createGraphics();
        g.translate((newWidth - width) / 2, (newHeight - height) / 2);
        g.rotate(radiansAngle, width / 2, height / 2);
        g.drawRenderedImage(image, null);
        g.dispose();
        return result;

    }

    private void initButtons(){

        this.leftTurnButton.addActionListener(e -> this.bufferedImage = this.rotateImage(this.bufferedImage, 45));

        this.rightTurnButton.addActionListener(e -> this.bufferedImage = this.rotateImage(this.bufferedImage, -45));

    }

    public BufferedImage getBufferedImage() {
        return this.bufferedImage;
    }

    public JButton getLeftTurnButton() {
        return this.leftTurnButton;
    }

    public JButton getRightTurnButton() {
        return this.rightTurnButton;
    }
}
