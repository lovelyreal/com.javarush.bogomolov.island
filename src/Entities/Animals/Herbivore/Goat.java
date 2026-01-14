package Entities.Animals.Herbivore;

import Entities.Animal;
import Entities.Plant;

import java.util.Map;

public class Goat extends Animal {
    public static int maxAmountInOneCell = 140;
    public Goat(int x, int y) {
        this.x = x;
        this.y = y;
        weight = 60D;
        maxCellsByMove = 3;
        killosOfMealToSatisfaction = 10;
        diet = Map.of(
                Plant.class, 100
        );
    }
    public String toString() {
        return "\uD83D\uDC10";
    }
}
