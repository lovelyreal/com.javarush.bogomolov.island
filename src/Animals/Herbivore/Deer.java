package Animals.Herbivore;

import Util.Animal;
import Util.Plant;

import java.util.Map;

public class Deer extends Animal {
    public static Integer maxEmountInOneCell = 20;
    public Deer() {
        weight = 300D;
        maxCellsByMove = 4;
        killosOfMealToSatisfaction = 50;
        diet = Map.of(
                Plant.class, 100
        );
    }
}
