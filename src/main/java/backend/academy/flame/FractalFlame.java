package backend.academy.flame;

import backend.academy.flame.cli.ConsoleCommunicator;
import backend.academy.flame.core.FractalCalculator;
import backend.academy.flame.graphic.ImageManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FractalFlame {

    private final ImageManager manager = new ImageManager();
    private final ConsoleCommunicator communicator = new ConsoleCommunicator();

    public void start() {
        var configs = communicator.communicate();
        var calculator = FractalCalculator.chooseRealisation(configs.threadsCount());
        var imageFractal = calculator.process(configs);
        log.info(configs.toString());
        manager.imageProcessor(imageFractal);
    }
}
