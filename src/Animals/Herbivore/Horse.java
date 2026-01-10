package Animals.Herbivore;

import Util.Animal;
import Util.Plant;

import java.util.Map;

public class Horse extends Animal {
    public Horse() {
        weight = 400D;
        maxCellsByMove = 4;
        killosOfMealToSatisfaction = 60;
        diet = Map.of(
                Plant.class, 100
        );
    }
}
