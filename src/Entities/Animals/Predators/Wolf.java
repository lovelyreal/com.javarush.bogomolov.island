package Entities.Animals.Predators;

import Entities.Animal;
import Entities.Animals.Herbivore.*;
import Util.Eatable;
import Util.Randomizer;
import service.Island;

import java.nio.file.DirectoryStream;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class Wolf extends Animal {
    public static int maxAmountInOneCell = 30;
    public Wolf(int x, int y) {
        this.x = x;
        this.y = y;

        weight = 50D;
        maxCellsByMove = 3;
        killosOfMealToSatisfaction = 8;
        diet = Map.of(
                Horse.class, 10,
                Deer.class, 15,
                Rabbit.class, 60,
                Mouse.class, 80,
                Goat.class, 60,
                Sheep.class, 70,
                Boar.class, 15,
                Buffalo.class, 10,
                Duck.class, 40
        );
    }

    public boolean canEat(Class<? extends Eatable> meal) {
        return diet.containsKey(meal);
    }

    @Override
    public String toString() {
        return "\uD83D\uDC3A";
    }




    public void  breed() {

    }


    public void eat(Eatable meal) {

    }

}
