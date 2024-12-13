package backend.academy.flame.graphic;

import backend.academy.flame.model.FractalImage;
import backend.academy.flame.model.ImageFormat;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;

@SuppressFBWarnings({"PATH_TRAVERSAL_IN"})
@Slf4j
public class ImageUtils {
    /**
     * Метод для создания и сохранения изображения из объекта FractalImage.
     *
     * @param fractal объект FractalImage, содержащий массив пикселей и размеры изображения.
     * @throws IOException если не удается записать изображение в файл.
     */
    public void saveImage(FractalImage fractal) {
        var outputFile = createFileToSaveIn(fractal.configs().transform().toString(), fractal.configs().format());
        BufferedImage image = new BufferedImage(fractal.width(), fractal.height(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < fractal.height(); y++) {
            for (int x = 0; x < fractal.width(); x++) {
                var pixel = fractal.pixels()[x][y];
                int rgb = new Color(pixel.red(), pixel.green(), pixel.blue()).getRGB();
                image.setRGB(x, y, rgb);
            }
        }

        try {
            ImageIO.write(image, fractal.configs().format().toString().toLowerCase(), outputFile);
        } catch (IOException e) {
            log.error("Ошибка при попытке сохранить файл изображения: {}", e.getMessage());
        }
    }

    private static File createFileToSaveIn(String name, ImageFormat format) {
        String fileName = "fractal_" + name + "." + format.toString().toLowerCase();

        File currentDir = new File(new File(System.getProperty("user.dir")), "images");
        File outputFile = new File(currentDir, fileName);

        if (outputFile.exists()) {
            if (!outputFile.delete()) {
                log.error("Не удалось удалить существующий файл: {}", outputFile.getAbsolutePath());
            }
        }

        try {
            outputFile.createNewFile();
        } catch (IOException e) {
            log.error("Ошибка при создании файла: {}", e.getMessage());
        }
        return outputFile;
    }
}
