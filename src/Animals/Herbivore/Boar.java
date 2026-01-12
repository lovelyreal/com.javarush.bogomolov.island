package Animals.Herbivore;

import Util.Animal;
import Util.Plant;

import java.util.Map;

public class Boar extends Animal{
    public static int maxAmountInOneCell = 50;
    public Boar(int x, int y) {
        this.x = x;
        this.y = y;
        weight = 400D;
        maxCellsByMove = 2;
        killosOfMealToSatisfaction = 50;
        diet = Map.of(
                Plant.class, 100,
                Caterpillar.class,90,
                Mouse.class, 50
        );
    }
    public String toString() {
        return "\uD83D\uDC17";
    }
}
