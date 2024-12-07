package backend.academy.flame.graphic;

import backend.academy.flame.model.FractalImage;
import backend.academy.flame.model.Pixel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ImageDisplayed {
    /**
     * Метод для отображения изображения на основе FractalImage.
     *
     * @param fractalImage объект FractalImage, содержащий данные о пикселях.
     */
    public void display(FractalImage fractalImage) {
        BufferedImage image = new BufferedImage(fractalImage.width(), fractalImage.height(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < fractalImage.height(); y++) {
            for (int x = 0; x < fractalImage.width(); x++) {
                Pixel pixel = fractalImage.pixels()[x][y];
                int rgb = new Color(pixel.red(), pixel.green(), pixel.blue()).getRGB();
                image.setRGB(x, y, rgb);
            }
        }

        JFrame frame = new JFrame("Fractal Display");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(fractalImage.width(), fractalImage.height());
        frame.setResizable(false);

        frame.add(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, null);
            }
        });

        frame.setVisible(true);
    }
}
