package backend.academy.flame.model;

public record FractalImage(Configs configs,
                           Pixel[][] pixels,
                           int width,
                           int height) {
}
