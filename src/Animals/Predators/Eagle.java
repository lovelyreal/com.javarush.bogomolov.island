package Animals.Predators;

import Animals.Herbivore.*;
import Util.Animal;

import java.util.Map;

public class Eagle extends Animal {
    public static Integer maxEmountInOneCell = 20;
    public Eagle() {
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
}
