package Animals.Herbivore;

import Util.Animal;
import Util.Plant;

import java.util.Map;

public class Mouse extends Animal {
    public static Integer maxEmountInOneCell = 500;
    public Mouse() {
        weight = 0.05D;
        maxCellsByMove = 1;
        killosOfMealToSatisfaction = 0.01;
        diet = Map.of(
                Plant.class, 100,
                Caterpillar.class, 90
        );
    }
}
