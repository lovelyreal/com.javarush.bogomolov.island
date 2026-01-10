package Animals.Predators;

import Animals.Herbivore.*;
import Util.Animal;

import java.util.Map;

public class Fox extends Animal {
    public static Integer maxEmountInOneCell = 30;
    public Fox() {
        weight = 8D;
        maxCellsByMove = 1;
        killosOfMealToSatisfaction = 3;
        diet = Map.of(
                Caterpillar.class, 40,
                Rabbit.class, 70,
                Mouse.class, 90,
                Duck.class, 60
        );
    }
}
