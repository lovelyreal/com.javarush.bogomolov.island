package Animals.Herbivore;

import Util.Animal;
import Util.Plant;

import java.util.Map;

public class Sheep extends Animal {
    public static Integer maxEmountInOneCell = 140;
    public Sheep() {
        weight = 70D;
        maxCellsByMove = 3;
        killosOfMealToSatisfaction = 15;
        diet = Map.of(
                Plant.class, 100
        );
    }
}
