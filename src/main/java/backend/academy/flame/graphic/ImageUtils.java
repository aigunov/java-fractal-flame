package backend.academy.flame.graphic;

import backend.academy.flame.model.FractalImage;
import lombok.extern.slf4j.Slf4j;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Slf4j
public class ImageUtils {
    /**
     * Метод для создания и сохранения изображения из объекта FractalImage.
     *
     * @param fractalImage объект FractalImage, содержащий массив пикселей и размеры изображения.
     * @throws IOException если не удается записать изображение в файл.
     */
    public void saveImage(FractalImage fractalImage) {
        var outputFile = createFileToSaveIn();
        BufferedImage image = new BufferedImage(fractalImage.width(), fractalImage.height(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < fractalImage.height(); y++) {
            for (int x = 0; x < fractalImage.width(); x++) {
                var pixel = fractalImage.pixels()[x][y];
                int rgb = new Color(pixel.red(), pixel.green(), pixel.blue()).getRGB();
                image.setRGB(x, y, rgb);
            }
        }
        try {
            ImageIO.write(image, "png", outputFile);
        } catch (IOException e) {
            log.error("Ошибка при попытке сохранить файл изображения: {}", e.getMessage());
        }
    }

    private static File createFileToSaveIn(){
        String fileName = "fractal_" + System.currentTimeMillis() + ".png";

        File currentDir = new File(System.getProperty("user.dir"));

        File outputFile = new File(currentDir, fileName);
        try {
            outputFile.createNewFile();
        } catch (IOException e) {
            log.error("Ошибка при создании файла: {}", e.getMessage());
        }
        return outputFile;
    }
}
