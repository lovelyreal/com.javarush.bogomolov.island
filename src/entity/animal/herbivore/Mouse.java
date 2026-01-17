package entity.animal.herbivore;

import entity.Animal;
import entity.Plant;

import java.util.Map;

public class Mouse extends Animal {
    public static int maxAmountInOneCell = 500;
    public Mouse(int x, int y) {
        this.mapPositionX = x;
        this.mapPositionY = y;
        weight = 0.05D;
        maxCellsByMove = 1;
        killosOfMealToSatisfaction = 0.01;
        diet = Map.of(
                Plant.class, 100,
                Caterpillar.class, 90
        );
    }
    public String toString() {
        return "\uD83D\uDC01";
    }
}
