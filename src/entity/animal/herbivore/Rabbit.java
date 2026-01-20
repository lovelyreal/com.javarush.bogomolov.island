package entity.animal.herbivore;

import entity.Animal;
import entity.Plant;

import java.util.Map;

public class Rabbit extends Animal {
    public static int maxAmountInOneCell = 150;
    public Rabbit(int x, int y) {
        this.mapPositionX = x;
        this.mapPositionY = y;
        weight = 2D;
        maxCellsByMove = 2;
        killosOfMealToSatisfaction = 0.45;
        currentKillosOfMeal = killosOfMealToSatisfaction / 2;
        diet = Map.of(
                Plant.class, 100
        );
    }
    public String toString() {
        return "\uD83D\uDC07";
    }
}
