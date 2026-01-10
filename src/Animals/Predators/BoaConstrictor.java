package Animals.Predators;

import Animals.Herbivore.*;
import Util.Animal;

import java.util.Map;

//Удав
public class BoaConstrictor extends Animal {
    public static Integer maxEmountInOneCell = 30;
    public BoaConstrictor() {
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
}
