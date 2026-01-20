package entity.animal.herbivore;

import entity.Animal;
import entity.Plant;

import java.util.Map;

public class Caterpillar extends Animal {
    public static int maxAmountInOneCell = 1000;
    public Caterpillar(int x, int y) {
        this.mapPositionX = x;
        this.mapPositionY = y;
        weight = 0.01D;
        maxCellsByMove = 0;
        killosOfMealToSatisfaction = 0;
        diet = Map.of(
                Plant.class, 100
        );
    }
    public String toString() {
        return "\uD83D\uDC1B";
    }

    @Override
    public boolean isAlive() {
        return true;
    }
}
