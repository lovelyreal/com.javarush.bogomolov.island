package entity.animal.predator;

import entity.Animal;
import entity.animal.herbivore.Duck;
import entity.animal.herbivore.Mouse;
import entity.animal.herbivore.Rabbit;

import java.util.Map;

//Удав
public class BoaConstrictor extends Animal {
    public static int maxAmountInOneCell = 30;
    public BoaConstrictor(int x, int y) {
        this.mapPositionX = x;
        this.mapPositionY = y;
        weight = 15D;
        maxCellsByMove = 1;
        killosOfMealToSatisfaction = 3;
        currentKillosOfMeal = killosOfMealToSatisfaction / 2;
        diet = Map.of(
                Fox.class, 15,
                Rabbit.class, 20,
                Mouse.class, 40,
                Duck.class, 10
        );
    }
    public String toString() {
        return "\uD83D\uDC0D";
    }
}
