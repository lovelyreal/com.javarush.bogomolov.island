package entity.animal.herbivore;

import entity.Animal;
import entity.Plant;

import java.util.Map;

public class Horse extends Animal {
    public static int maxAmountInOneCell = 20;
    public Horse(int x, int y) {
        this.mapPositionX = x;
        this.mapPositionY = y;
        weight = 400D;
        maxCellsByMove = 4;
        killosOfMealToSatisfaction = 60;
        diet = Map.of(
                Plant.class, 100
        );
    }
    public String toString() {
        return "\uD83D\uDC34";
    }
}
