package backend.academy.flame;

import backend.academy.flame.cli.ConsoleCommunicator;
import backend.academy.flame.cli.ReportGenerator;
import backend.academy.flame.core.CoefficientGenerator;
import backend.academy.flame.core.FractalCalculator;
import backend.academy.flame.graphic.ImageDisplayed;
import backend.academy.flame.graphic.ImageManager;
import backend.academy.flame.graphic.ImageUtils;
import backend.academy.flame.model.Configs;
import backend.academy.flame.model.TransformationFunction;

public class FractalFlame {
    private final ConsoleCommunicator communicator = new ConsoleCommunicator();
    private final ReportGenerator reporter = new ReportGenerator();
    private final CoefficientGenerator generator = new CoefficientGenerator();
    private final FractalCalculator calculator = new FractalCalculator(generator);
    private final ImageManager manager = new ImageManager();
    private final ImageDisplayed displayed = new ImageDisplayed();
    private final ImageUtils imageUtils = new ImageUtils();
    public void start() {

        Configs configs = Configs.builder()
            .height(1080)
            .width(1920)
            .iterationCount(15_000_000)
            .affineCount(15)
            .symmetry(3)
            .transform(TransformationFunction.HEART)
            .build();
//        configs = communicator.communicate();
        System.out.println(configs);
        var imageFractal = calculator.render(configs);
//        imageUtils.saveImage(imageFractal);
        displayed.display(imageFractal);
    }
}
