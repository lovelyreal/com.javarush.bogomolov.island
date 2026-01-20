package entity.animal.predator;

import entity.Animal;
import entity.animal.herbivore.Caterpillar;
import entity.animal.herbivore.Duck;
import entity.animal.herbivore.Mouse;
import entity.animal.herbivore.Rabbit;

import java.util.Map;

public class Fox extends Animal {
    public static int maxAmountInOneCell = 30;
    public Fox(int x, int y) {
        this.mapPositionX = x;
        this.mapPositionY = y;
        weight = 8D;
        maxCellsByMove = 1;
        killosOfMealToSatisfaction = 3;
        currentKillosOfMeal = killosOfMealToSatisfaction / 2;
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
