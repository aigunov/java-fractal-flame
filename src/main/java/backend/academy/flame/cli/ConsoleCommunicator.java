package backend.academy.flame.cli;

import backend.academy.flame.model.Configs;
import backend.academy.flame.model.ImageFormat;
import backend.academy.flame.model.TransformationFunction;
import lombok.Getter;
import lombok.Setter;
import java.util.NoSuchElementException;
import java.util.Scanner;

@Getter
@Setter
public class ConsoleCommunicator {
    private Scanner scanner = new Scanner(System.in);
    public Configs communicate() {

        System.out.println("Введите высоту изображения (целое положительное число):");
        int height;
        while (true) {
            height = getIntInput();
            if (height > 0) {
                break;
            }
            System.out.println("Ошибка! Введите положительное число.");
        }

        System.out.println("Введите ширину изображения (целое положительное число):");
        int width;
        while (true) {
            width = getIntInput();
            if (width > 0) {
                break;
            }
            System.out.println("Ошибка! Введите положительное число.");
        }

        System.out.println("Введите количество аффинных преобразований (не менее 2):");
        int affinsCount;
        while (true) {
            affinsCount = getIntInput();
            if (affinsCount >= 2) {
                break;
            }
            System.out.println("Ошибка! Количество аффинных преобразований должно быть не менее 2.");
        }

        System.out.println("Введите количество итераций (не менее 100):");
        int iterations;
        while (true) {
            iterations = getIntInput();
            if (iterations >= 100) {
                break;
            }
            System.out.println("Ошибка! Количество итераций должно быть не менее 100.");
        }

        System.out.println("Введите количество желаемых симметрий от 1 до 6(по умолчанию 1):");
        int symmetry = 1;
        while (true) {
            symmetry = getIntInput();
            if (symmetry > 0 && symmetry < 7) {
                break;
            }
            System.out.println("Ошибка! Количество симметрий должно быть не менее 100.");
        }

        System.out.println("Выберите нелинейное преобразование, указав число от 1 до 5:");
        System.out.println("1 - SINUS");
        System.out.println("2 - HEART");
        System.out.println("3 - SPHERE");
        System.out.println("4 - POLAR");
        System.out.println("5 - DISK");

        int transformationChoiceNumber;
        while (true) {
            transformationChoiceNumber = getIntInput();
            if (transformationChoiceNumber >= 1 && transformationChoiceNumber <= 5) {
                break;
            }
            System.out.println("Ошибка! Укажите число от 1 до 5.");
        }

        System.out.println("Выберите один из трех форматов изображения(введите только цифру):");
        System.out.println("1. PNG");
        System.out.println("2. JPEG");
        System.out.println("3. BMP");
        int imageFormatNumber;
        ImageFormat format;
        while (true) {
            imageFormatNumber = getIntInput();
            if (imageFormatNumber >= 1 && imageFormatNumber <= 3) {
                format = switch (imageFormatNumber){
                    case 1 -> ImageFormat.PNG;
                    case 2 -> ImageFormat.JPEG;
                    case 3 -> ImageFormat.BMP;
                    default -> throw new IllegalArgumentException("Такого варианта нет, там где-то ошибка.");
                };
                break;
            }
            System.out.println("Ошибка!");
        }

        System.out.println("Выберите желаемое количество потоков для выполнения программы (от 1 до 5)");
        int threadsCount;
        while (true) {
            threadsCount = getIntInput();
            if (threadsCount >= 1 && threadsCount <= 5) {
                break;
            }
            System.out.println("Ошибка! Укажите число потоков от 1 до 5.");
        }

        Configs configs = Configs.builder()
            .height(height)
            .width(width)
            .iterationCount(iterations)
            .affineCount(affinsCount)
            .symmetry(symmetry)
            .transform(transformation(transformationChoiceNumber))
            .format(format)
            .threadsCount(threadsCount)
            .build();

        System.out.println("Введенные параметры:");
        System.out.println("Высота: " + height);
        System.out.println("Ширина: " + width);
        System.out.println("Итерации: " + iterations);
        System.out.println("Выбранное преобразование: " + transformation(transformationChoiceNumber));

        return configs;
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
}

