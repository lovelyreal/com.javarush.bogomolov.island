package entity.animal.herbivore;

import entity.Animal;
import entity.Plant;

import java.util.Map;

public class Deer extends Animal {
    public static int maxAmountInOneCell = 20;
    public Deer(int x, int y) {
        this.mapPositionX = x;
        this.mapPositionY = y;
        weight = 300D;
        maxCellsByMove = 4;
        killosOfMealToSatisfaction = 50;
        currentKillosOfMeal = killosOfMealToSatisfaction / 2;
        diet = Map.of(
                Plant.class, 100
        );
    }
    public String toString() {
        return "\uD83E\uDD8C";
    }
}
