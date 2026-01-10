package Animals.Herbivore;

import Util.Animal;
import Util.Plant;

import java.util.Map;

public class Goat extends Animal {
    public Goat() {
        weight = 60D;
        maxCellsByMove = 3;
        killosOfMealToSatisfaction = 10;
        diet = Map.of(
                Plant.class, 100
        );
    }
}
