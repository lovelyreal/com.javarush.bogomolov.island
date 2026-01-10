package Animals.Herbivore;

import Util.Animal;
import Util.Plant;

import java.util.Map;

public class Caterpillar extends Animal {
    public Caterpillar() {
        weight = 0.01D;
        maxCellsByMove = 0;
        killosOfMealToSatisfaction = 0;
        diet = Map.of(
                Plant.class, 100
        );
    }
}
