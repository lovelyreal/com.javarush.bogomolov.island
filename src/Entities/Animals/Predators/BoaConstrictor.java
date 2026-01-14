package Entities.Animals.Predators;

import Entities.Animal;
import Entities.Animals.Herbivore.Duck;
import Entities.Animals.Herbivore.Mouse;
import Entities.Animals.Herbivore.Rabbit;

import java.util.Map;

//Удав
public class BoaConstrictor extends Animal {
    public static int maxAmountInOneCell = 30;
    public BoaConstrictor(int x, int y) {
        this.x = x;
        this.y = y;
        weight = 15D;
        maxCellsByMove = 1;
        killosOfMealToSatisfaction = 3;
        diet = Map.of(
                Fox.class, 15,
                Rabbit.class, 20,
                Mouse.class, 40,
                Duck.class, 10
        );
    }
    public String toString() {
        return "\uD83D\uDC0D";
    }
}
