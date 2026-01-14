package Entities.Animals.Herbivore;

import Entities.Animal;
import Entities.Plant;

import java.util.Map;

public class Deer extends Animal {
    public static int maxAmountInOneCell = 20;
    public Deer(int x, int y) {
        this.x = x;
        this.y = y;
        weight = 300D;
        maxCellsByMove = 4;
        killosOfMealToSatisfaction = 50;
        diet = Map.of(
                Plant.class, 100
        );
    }
    public String toString() {
        return "\uD83E\uDD8C";
    }
}
