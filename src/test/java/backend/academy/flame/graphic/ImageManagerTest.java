package backend.academy.flame.graphic;

import backend.academy.flame.model.FractalImage;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ImageManagerTest {

    private ImageManager imageManager;
    private ImageUtils mockUtils;
    private ImageDisplayed mockDisplayed;
    private FractalImage mockImage;

    @BeforeEach
    public void setUp() {
        mockUtils = mock(ImageUtils.class);
        mockDisplayed = mock(ImageDisplayed.class);
        imageManager = new ImageManager();
        imageManager.utils(mockUtils).displayed(mockDisplayed);
        mockImage = mock(FractalImage.class);
    }

    @Test
    void testImageProcessorCallsSaveAndDisplay() {
        imageManager.imageProcessor(mockImage);

        verify(mockUtils, times(1)).saveImage(mockImage);
        verify(mockDisplayed, times(1)).display(mockImage);
    }

    @Test
    void testImageProcessorOutputMessage() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        imageManager.imageProcessor(mockImage);

        String output = outputStream.toString().trim();
        assertEquals("Обработка изображения завершена", output);
    }
}
