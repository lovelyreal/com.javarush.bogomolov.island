package Animals.Herbivore;

import Util.Animal;
import Util.Plant;

import java.util.Map;

public class Duck extends Animal {
    public static Integer maxEmountInOneCell = 200;
    public Duck() {
        weight = 1D;
        maxCellsByMove = 4;
        killosOfMealToSatisfaction = 0.15;
        diet = Map.of(
                Plant.class, 100,
                Caterpillar.class, 90
        );
    }
}
