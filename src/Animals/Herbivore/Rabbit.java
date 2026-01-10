package Animals.Herbivore;

import Util.Animal;
import Util.Plant;

import java.util.Map;

public class Rabbit extends Animal {
    public Rabbit() {
        weight = 2D;
        maxCellsByMove = 2;
        killosOfMealToSatisfaction = 0.45;
        diet = Map.of(
                Plant.class, 100
        );
    }
}
