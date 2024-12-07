package backend.academy.flame.model;

import lombok.Getter;

@Getter
public enum Palette {
    VIOLET(128, 0, 128),
    BLUE(0, 0, 255),
    LAVENDER(181, 126, 220),
    INDIGO(75, 0, 130),
    TURQUOISE(64, 224, 208),
    RED(255, 0, 0),
    GREEN(0, 255, 0),
    YELLOW(255, 255, 0),
    ORANGE(255, 165, 0),
    PINK(255, 192, 203),
    TEAL(0, 128, 128),
    CYAN(0, 255, 255),
    MAGENTA(255, 0, 255),
    LIME(50, 205, 50),
    GOLD(255, 215, 0),
    SALMON(250, 128, 114),
    MAROON(128, 0, 0),
    OLIVE(128, 128, 0),
    NAVY(0, 0, 128),
    SILVER(192, 192, 192),
    DARK_GRAY(64, 64, 64),
    LIGHT_GRAY(211, 211, 211);

    private int red;
    private int green;
    private int blue;

    Palette(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
}
