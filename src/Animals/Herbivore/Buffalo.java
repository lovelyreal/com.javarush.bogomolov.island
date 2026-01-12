package Animals.Herbivore;

import Util.Animal;
import Util.Plant;

import java.util.Map;

public class Buffalo extends Animal {
    public static int maxAmountInOneCell = 10;
    public Buffalo(int x, int y) {
        this.x = x;
        this.y = y;
        weight = 700D;
        maxCellsByMove = 3;
        killosOfMealToSatisfaction = 100;
        diet = Map.of(
                Plant.class, 100
        );
    }
    public String toString() {
        return "\uD83D\uDC03";
    }
}
