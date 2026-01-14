package Entities.Animals.Predators;

import Entities.Animal;
import Entities.Animals.Herbivore.Duck;
import Entities.Animals.Herbivore.Mouse;
import Entities.Animals.Herbivore.Rabbit;

import java.util.Map;

public class Eagle extends Animal {
    public static int maxAmountInOneCell = 20;
    public Eagle(int x, int y) {
        this.x = x;
        this.y = y;
        weight = 6D;
        maxCellsByMove = 3;
        killosOfMealToSatisfaction = 1;
        diet = Map.of(
                Fox.class, 10,
                Rabbit.class, 90,
                Mouse.class, 90,
                Duck.class, 80
        );
    }
    public String toString() {
        return "\uD83E\uDD85";
    }
}
