package Animals.Herbivore;

import Util.Animal;
import Util.Plant;

import java.util.Map;

public class Sheep extends Animal {
    public static int maxAmountInOneCell = 140;
    public Sheep(int x, int y) {
        this.x = x;
        this.y = y;
        weight = 70D;
        maxCellsByMove = 3;
        killosOfMealToSatisfaction = 15;
        diet = Map.of(
                Plant.class, 100
        );
    }
    public String toString() {
        return "\uD83D\uDC11";
    }
}
