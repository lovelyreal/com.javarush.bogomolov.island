package entity.animal.herbivore;

import entity.Animal;
import entity.Plant;

import java.util.Map;

public class Sheep extends Animal {
    public static int maxAmountInOneCell = 140;
    public Sheep(int x, int y) {
        this.mapPositionX = x;
        this.mapPositionY = y;
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
