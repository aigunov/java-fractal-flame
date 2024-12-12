package backend.academy.flame.cli;

import backend.academy.flame.model.Configs;
import java.io.PrintStream;
import java.time.Duration;

public class ReportGenerator {
    private PrintStream out;

    public ReportGenerator(PrintStream out) {
        this.out = out;
    }

    public void generateReport(Configs configs, long processingTime) {
        out.println("| Характеристика | Значение |");
        out.println("|---|---|");
        out.println("| Размер изображения | " + configs.width() + "*" + configs.height() + " |");
        out.println("| Трансформация | " + configs.transform() + " |");
        out.println("| Количество итераций | " + configs.iterationCount() + " |");
        out.println("| Симметрия | " + configs.symmetry() + " |");
        out.println("| Время работы алгоритма | " + formatDuration(processingTime) + " |");
        out.println("| Количество потоков | " + configs.threadsCount() + " |");
    }

    private String formatDuration(long processingTime) {
        Duration duration = Duration.ofMillis(processingTime);
        long seconds = duration.getSeconds();
        long millis = duration.toMillisPart();
        return String.format("%d сек. %d мс", seconds, millis);

    }

}
