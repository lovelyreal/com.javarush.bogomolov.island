package Animals.Herbivore;

import Util.Animal;
import Util.Plant;

import java.util.Map;

public class Boar extends Animal{
    public Boar() {
        weight = 400D;
        maxCellsByMove = 2;
        killosOfMealToSatisfaction = 50;
        diet = Map.of(
                Plant.class, 100,
                Caterpillar.class,90,
                Mouse.class, 50
        );
    }
}
