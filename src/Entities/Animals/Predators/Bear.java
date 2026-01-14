package Entities.Animals.Predators;

import Entities.Animal;
import Entities.Animals.Herbivore.*;

import java.util.Map;

public class Bear extends Animal {
    public static int maxAmountInOneCell = 5;
    public Bear(int x, int y) {
        this.x = x;
        this.y = y;
        weight = 500D;
        maxCellsByMove = 2;
        killosOfMealToSatisfaction = 80;
        diet = Map.of(
                BoaConstrictor.class,80,
                Horse.class, 40,
                Deer.class, 80,
                Rabbit.class, 80,
                Mouse.class, 90,
                Goat.class, 70,
                Sheep.class, 70,
                Boar.class, 50,
                Buffalo.class, 20,
                Duck.class, 10


        );
    }
    public String toString() {
        return "\uD83D\uDC3B";
    }
}
