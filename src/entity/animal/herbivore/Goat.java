package entity.animal.herbivore;

import entity.Animal;
import entity.Plant;

import java.util.Map;

public class Goat extends Animal {
    public static int maxAmountInOneCell = 140;
    public Goat(int x, int y) {
        this.mapPositionX = x;
        this.mapPositionY = y;
        weight = 60D;
        maxCellsByMove = 3;
        killosOfMealToSatisfaction = 10;
        currentKillosOfMeal = killosOfMealToSatisfaction / 2;
        diet = Map.of(
                Plant.class, 100
        );
    }
    public String toString() {
        return "\uD83D\uDC10";
    }
}
