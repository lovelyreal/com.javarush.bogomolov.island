package Animals.Herbivore;

import Util.Animal;
import Util.Plant;

import java.util.Map;

public class Buffalo extends Animal {
    public static Integer maxEmountInOneCell = 10;
    public Buffalo() {
        weight = 700D;
        maxCellsByMove = 3;
        killosOfMealToSatisfaction = 100;
        diet = Map.of(
                Plant.class, 100
        );
    }
}
