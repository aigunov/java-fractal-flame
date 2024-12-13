package backend.academy.flame;

import backend.academy.flame.cli.ConsoleCommunicator;
import backend.academy.flame.core.FractalCalculator;
import backend.academy.flame.graphic.ImageManager;

/**
 * Главный класс проекта,
 * через запуск метода start которого
 * начинается вся генерация
 */
public class FractalFlame {

    private final ImageManager manager = new ImageManager();
    private final ConsoleCommunicator communicator = new ConsoleCommunicator();

    public void start() {
        var configs = communicator.communicate();
        var calculator = FractalCalculator.chooseRealisation(configs.threadsCount());
        var imageFractal = calculator.process(configs);
        manager.imageProcessor(imageFractal);
    }
}
