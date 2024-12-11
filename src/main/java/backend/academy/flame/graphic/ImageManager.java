package backend.academy.flame.graphic;

import backend.academy.flame.model.FractalImage;
import lombok.Getter;
import lombok.Setter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
public class ImageManager {
    private ImageDisplayed displayed;
    private ImageUtils utils;

    public ImageManager() {
        this.utils = new ImageUtils();
        this.displayed = new ImageDisplayed();
    }

    public void imageProcessor(FractalImage image){
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> utils.saveImage(image));
        executor.submit(() -> displayed.display(image));

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            throw new Error("Fenita la camedia: " + e.getMessage());
        }

        System.out.println("Обработка изображения завершена");
    }
}
