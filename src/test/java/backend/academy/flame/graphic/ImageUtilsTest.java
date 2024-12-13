package backend.academy.flame.graphic;

import backend.academy.flame.model.Configs;
import backend.academy.flame.model.FractalImage;
import backend.academy.flame.model.Pixel;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ImageUtilsTest {
    @Test
    void imageProcessor_callsSaveAndDisplayMethods() {
        ImageUtils mockUtils = mock(ImageUtils.class);
        ImageDisplayed mockDisplayed = mock(ImageDisplayed.class);

        ImageManager manager = new ImageManager();
        manager.utils(mockUtils);
        manager.displayed(mockDisplayed);

        Configs configs = Configs.builder().build(); // Replace with appropriate Configs constructor
        Pixel[][] pixels = new Pixel[100][100];
        FractalImage image = new FractalImage(configs, pixels, 100, 100);

        manager.imageProcessor(image);

        verify(mockUtils, times(1)).saveImage(image);
        verify(mockDisplayed, times(1)).display(image);
    }

}
