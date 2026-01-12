package Animals.Herbivore;

import Util.Animal;
import Util.Plant;

import java.util.Map;

public class Rabbit extends Animal {
    public static int maxAmountInOneCell = 150;
    public Rabbit(int x, int y) {
        this.x = x;
        this.y = y;
        weight = 2D;
        maxCellsByMove = 2;
        killosOfMealToSatisfaction = 0.45;
        diet = Map.of(
                Plant.class, 100
        );
    }
    public String toString() {
        return "\uD83D\uDC07";
    }
}
