package backend.academy.flame.core;

import backend.academy.flame.model.Configs;
import backend.academy.flame.model.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings({"MagicNumber"})
@Slf4j
@Getter
@Setter
public class MultiThreadCalculator extends FractalCalculator {
    private final ExecutorService executor;

    public MultiThreadCalculator(int threadCount) {
        this.executor = Executors.newFixedThreadPool(threadCount);
    }

    @Override
    public void render(Configs configs) {
        List<Future<?>> tasks = new ArrayList<>();
        var coefficients = coefficientGenerator.generateCoefficientsCompression(configs.affineCount());

        int iterationsPerThread = configs.iterationCount() / ((ThreadPoolExecutor) executor).getCorePoolSize();

        for (int t = 0; t < ((ThreadPoolExecutor) executor).getCorePoolSize(); t++) {
            tasks.add(executor.submit(
                () -> {
                double newX = ThreadLocalRandom.current().nextDouble(xMin, xMax);
                double newY = ThreadLocalRandom.current().nextDouble(yMin, yMax);
                for (int i = -30; i < iterationsPerThread; i++) {
                    int idx = ThreadLocalRandom.current().nextInt(0, configs.affineCount());
                    var coeff = coefficients.get(idx);
                    var x = newX * coeff.a() + newY * coeff.b() + coeff.c();
                    var y = newX * coeff.d() + newY * coeff.e() + coeff.f();
                    if (i > 0) {
                        var point = transformation.apply(new Point(x, y));
                        double theta = 0.0;
                        for (int s = 0; s < configs.symmetry(); theta += Math.PI * 2 / configs.symmetry(), ++s) {
                            var rotatedPoint = rotate(point, theta, configs.width(), configs.height());
                            double normalizedX = (xMax - rotatedPoint.x()) / (xMax - xMin);
                            double normalizedY = (yMax - rotatedPoint.y()) / (yMax - yMin);
                            int pixelX = (int) (configs.width() - normalizedX * configs.width());
                            int pixelY = (int) (configs.height() - normalizedY * configs.height());
                            if (pixelX >= 0 && pixelY >= 0 && pixelX < configs.width() && pixelY < configs.height()) {
                                synchronized (pixels[pixelX][pixelY]) {
                                    var pixel = pixels[pixelX][pixelY];
                                    if (pixel.count() == 0) {
                                        pixel.red(coeff.palette().red());
                                        pixel.green(coeff.palette().green());
                                        pixel.blue(coeff.palette().blue());
                                    } else {
                                        pixel.red((pixel.red() + coeff.palette().red()) / 2);
                                        pixel.green((pixel.green() + coeff.palette().green()) / 2);
                                        pixel.blue((pixel.blue() + coeff.palette().blue()) / 2);
                                    }
                                    pixel.count(pixel.count() + 1);
                                }
                            }
                        }
                    }
                    newX = x;
                    newY = y;
                }
            }));
        }

        for (Future<?> task : tasks) {
            try {
                task.get();
            } catch (InterruptedException | ExecutionException e) {
                log.error("Произошла ошибка многопоточной генерации фрактального пламени: {}", e.getMessage());
            }
        }

        executor.shutdown();
    }
}
