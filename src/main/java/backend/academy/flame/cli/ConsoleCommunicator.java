package backend.academy.flame.cli;

import backend.academy.flame.model.Configs;
import backend.academy.flame.model.ImageFormat;
import backend.academy.flame.model.TransformationFunction;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Scanner;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс для сбора информации у пользователя через консоль
 */
@SuppressWarnings({"RegexpSinglelineJava", "MagicNumber"})
@Getter
@Setter
public class ConsoleCommunicator {

    private Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

     public Configs communicate() {
         var message = "Ошибка! Введите положительное число.";
         int height = getIntInputWithCondition("Введите высоту изображения (целое положительное число):",
             value -> value > 0, message);
         int width = getIntInputWithCondition("Введите ширину изображения (целое положительное число):",
             value -> value > 0, message);
         int affinsCount = getIntInputWithCondition("Введите количество аффинных преобразований (не менее 2):",
             value -> value >= 2, "Ошибка! Количество аффинных преобразований должно быть не менее 2.");
         int iterations = getIntInputWithCondition("Введите количество итераций (не менее 100):",
             value -> value >= 100, "Ошибка! Количество итераций должно быть не менее 100.");
         int symmetry = getIntInputWithCondition("Введите количество желаемых симметрий от 1 до "
                 + "6(по умолчанию 1):",
             value -> value > 0 && value < 7, "Ошибка! Количество симметрий должно быть от 1 до 6.");

         var str = """
             Выберите нелинейное преобразование, указав число от 1 до 5:
             1 - SINUS
             2 - HEART
             3 - SPHERE
             4 - POLAR
             5 - DISK
             """;
         int transformationChoiceNumber = getIntInputWithCondition(str,
             value -> value >= 1 && value <= 5, "Ошибка! Укажите число от 1 до 5.");
         ImageFormat format = getImageFormatInput();
         int threadsCount = getIntInputWithCondition("Выберите желаемое количество потоков для выполнения"
                 + " программы (от 1 до 5):",
             value -> value >= 1 && value <= 5, "Ошибка! Укажите число потоков от 1 до 5.");

         return Configs.builder()
            .height(height)
            .width(width)
            .iterationCount(iterations)
            .affineCount(affinsCount)
            .symmetry(symmetry)
            .transform(transformation(transformationChoiceNumber))
            .format(format)
            .threadsCount(threadsCount)
            .build();
     }

    private int getIntInputWithCondition(String prompt, IntCondition condition, String errorMsg) {
        int value;
        while (true) {
            System.out.println(prompt);
            value = getIntInput();
            if (condition.test(value)) {
                break;
            }
            System.out.println(errorMsg);
        }
        return value;
    }

    private ImageFormat getImageFormatInput() {
        System.out.println("Выберите один из трех форматов изображения(введите только цифру):");
        System.out.println("1. PNG");
        System.out.println("2. JPEG");
        System.out.println("3. BMP");

        int imageFormatNumber;
        while (true) {
            imageFormatNumber = getIntInput();
            switch (imageFormatNumber) {
                case 1: return ImageFormat.PNG;
                case 2: return ImageFormat.JPEG;
                case 3: return ImageFormat.BMP;
                default:
                    System.out.println("Ошибка! Укажите число от 1 до 3.");
            }
        }
    }

    private int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Ошибка! Введите целое число.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private TransformationFunction transformation(int choice) {
        return switch (choice) {
            case 1 -> TransformationFunction.SINUS;
            case 2 -> TransformationFunction.HEART;
            case 3 -> TransformationFunction.SPHERE;
            case 4 -> TransformationFunction.POLAR;
            case 5 -> TransformationFunction.DISK;
            default -> throw new NoSuchElementException("Invalid choice");
        };
    }

    private interface IntCondition {
        boolean test(int value);
    }
}

