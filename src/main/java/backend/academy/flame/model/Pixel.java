package backend.academy.flame.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pixel {
    private int x;
    private int y;
    private int count;
    private int red;
    private int green;
    private int blue;
    private double normal;
}
