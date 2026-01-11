package Animals.Predators;

import Animals.Herbivore.*;
import Util.Animal;

import java.util.Map;

public class Fox extends Animal {
    public static int maxEmountInOneCell = 30;
    public Fox(int x, int y) {
        this.x = x;
        this.y = y;
        weight = 8D;
        maxCellsByMove = 1;
        killosOfMealToSatisfaction = 3;
        diet = Map.of(
                Caterpillar.class, 40,
                Rabbit.class, 70,
                Mouse.class, 90,
                Duck.class, 60
        );
    }
    @Override
    public String toString() {
        return "\uD83E\uDD8A";
    }
}
