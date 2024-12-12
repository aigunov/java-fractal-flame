package backend.academy.flame;

import backend.academy.flame.cli.ConsoleCommunicator;
import backend.academy.flame.cli.ReportGenerator;
import backend.academy.flame.core.CoefficientGenerator;
import backend.academy.flame.core.FractalCalculator;
import backend.academy.flame.core.FractalCalculatorSingleThread;
import backend.academy.flame.graphic.ImageManager;
import backend.academy.flame.model.Configs;
import backend.academy.flame.model.ImageFormat;
import backend.academy.flame.model.TransformationFunction;

public class FractalFlame {
    private final ImageManager manager = new ImageManager();

    public void start() {

        Configs configs = Configs.builder()
            .height(800)
            .width(800)
            .iterationCount(15_000_000)
            .affineCount(15)
            .symmetry(3)
            .transform(TransformationFunction.SINUS  )
            .threadsCount(4)
            .format(ImageFormat.PNG)
            .build();
//        configs = communicator.communicate();
        var calculator = FractalCalculator.chooseRealisation(configs.threadsCount());
        System.out.println(configs);
        var imageFractal = calculator.render(configs);
        manager.imageProcessor(imageFractal);
    }
}
