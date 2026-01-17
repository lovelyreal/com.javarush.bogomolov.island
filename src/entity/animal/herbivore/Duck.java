package entity.animal.herbivore;

import entity.Animal;
import entity.Plant;

import java.util.Map;

public class Duck extends Animal {
    public static int maxAmountInOneCell = 200;
    public Duck(int x, int y) {
        this.mapPositionX = x;
        this.mapPositionY = y;
        weight = 1D;
        maxCellsByMove = 4;
        killosOfMealToSatisfaction = 0.15;
        diet = Map.of(
                Plant.class, 100,
                Caterpillar.class, 90
        );
    }
    public String toString() {
        return "\uD83E\uDD86";
    }
}
