package backend.academy;

import backend.academy.flame.FractalFlame;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        System.out.println("Добро пожаловать в генератор фрактального пламени!");
        FractalFlame flame = new FractalFlame();
        flame.start();
    }
}
