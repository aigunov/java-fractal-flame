package backend.academy.flame.core;

import backend.academy.flame.cli.ReportGenerator;
import backend.academy.flame.core.transforms.DiskTransform;
import backend.academy.flame.core.transforms.HeartTransform;
import backend.academy.flame.core.transforms.PolarTransform;
import backend.academy.flame.core.transforms.SinusTransform;
import backend.academy.flame.core.transforms.SphereTransform;
import backend.academy.flame.core.transforms.Transformation;
import backend.academy.flame.model.Configs;
import backend.academy.flame.model.FractalImage;
import backend.academy.flame.model.Pixel;
import backend.academy.flame.model.TransformationFunction;
import lombok.Getter;
import lombok.Setter;

/**
 * Абстрактный класс генератор пламени
 * Один абстрактный метод генерации и расчета точки
 * который отличается в зависимости от реализации
 * однопоточной или многопоточной
 */
@SuppressWarnings({"MagicNumber"})
@Getter
@Setter
public abstract class FractalCalculator {
    private ReportGenerator reportGenerator = new ReportGenerator(System.out);

    protected double xMax;
    protected double xMin;
    protected double yMax = 1.0;
    protected double yMin = -1.0;

    protected CoefficientGenerator coefficientGenerator;
    protected Pixel[][] pixels;
    protected Transformation transformation;

    public FractalCalculator() {
        coefficientGenerator = new CoefficientGenerator();
    }

    /**
     * Статический метод для создания объект конкретной реализации без создания фабрики
     * @param countOfThreads - параметр на который ориентируется метод при определении конкретной реализации
     * @return объект определенного класса наследника в зависимости от выбора пользователя
     */
    public static FractalCalculator chooseRealisation(int countOfThreads) {
        if (countOfThreads == 1) {
            return new FractalCalculatorSingleThread();
        } else {
            return new MultiThreadCalculator(countOfThreads);
        }
    }

    public abstract void render(Configs configs);

    /**
     * Главный управляющий метод класса
     * @param configs настройки системы пользователем
     * @return матрицу сгенерированных точек и другую информацию
     */
    public FractalImage process(Configs configs) {
        chooseTransformation(configs.transform());
        init(configs.width(), configs.height());

        long startTime = System.currentTimeMillis();

        render(configs);

        correction(configs.width(), configs.height());

        long endTime = System.currentTimeMillis();
        reportGenerator.generateReport(configs, endTime - startTime);
        return new FractalImage(configs, pixels, configs.width(), configs.height());
    }

    /**
     * Метод гамма коррекции итоговой матрицы
     * необходим чтобы избавиться от шумов на итоговой картине
     * посредством отображения частоты попадания в точку
     * на нормали логарифмическим образом
     * @param width - ширина изображения
     * @param height - высота изображения
     */
    private void correction(int width, int height) {
        double max = 0.0;
        double gamma = 0.6;
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                if (pixels[row][col].count() != 0) {
                    pixels[row][col].normal(Math.log10(pixels[row][col].count()));
                    if (pixels[row][col].normal() > max) {
                        max = pixels[row][col].normal();
                    }
                }
            }
        }
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                pixels[row][col].normal(pixels[row][col].normal() / max);
                pixels[row][col].red(
                    (int) (pixels[row][col].red() * Math.pow(pixels[row][col].normal(), (1.0 / gamma))));
                pixels[row][col].green(
                    (int) (pixels[row][col].green() * Math.pow(pixels[row][col].normal(), (1.0 / gamma))));
                pixels[row][col].blue(
                    (int) (pixels[row][col].blue() * Math.pow(pixels[row][col].normal(), (1.0 / gamma))));
            }
        }
    }

    private void init(int width, int height) {
        double aspectRatio = (double) width / height;
        xMax = aspectRatio / 2;
        xMin = -xMax;

        pixels = new Pixel[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[x][y] = new Pixel();
            }
        }

    }

    protected void chooseTransformation(TransformationFunction transform) {
        transformation = switch (transform) {
            case SINUS -> new SinusTransform();
            case DISK -> new DiskTransform();
            case HEART -> new HeartTransform();
            case POLAR -> new PolarTransform();
            case SPHERE -> new SphereTransform();
        };
    }
}
