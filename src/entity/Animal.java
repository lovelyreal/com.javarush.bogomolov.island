package entity;

import util.*;
import service.Island;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Animal implements Eatable {
    protected static int maxAmountInOneCell;
    protected Integer mapPositionX;
    protected Integer mapPositionY;
    protected double weight;
    protected Integer maxCellsByMove;
    protected double killosOfMealToSatisfaction;
    protected double currentKillosOfMeal = killosOfMealToSatisfaction;
    protected Map<Class<? extends Eatable>, Integer> diet;

    public void setMapPosition(int newX, int newY) {
        this.mapPositionX = newX;
        this.mapPositionY = newY;
    }

    public enum Direction {
        LEFT(-1, 0),
        RIGHT(1, 0),
        UP(0, -1),
        DOWN(0, 1),
        LEFT_UP(-1, -1),
        LEFT_DOWN(-1, 1),
        RIGHT_UP(1, -1),
        RIGHT_DOWN(1, 1);

        final int dx;
        final int dy;

        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        private static final Direction[] VALUES = values();

        public static Direction random() {
            return VALUES[Randomizer.generateNum(VALUES.length)];
        }

    }


    public Integer getMaxCellsByMove() {
        return maxCellsByMove;
    }


    public void move(Island island, Island.Location location) {
        Direction result = Direction.random();
        if (result == null) return;

        int newX = result.dx;
        int newY = result.dy;

        if (!Island.isValidPosition(newX, newY, this.getClass(), location)){ return;}

        Island.Location target = island.getLocations()[newX][newY];

        Island.Location first = location;
        Island.Location second = target;

        if (System.identityHashCode(first) > System.identityHashCode(second)) {
            first = target;
            second = location;
        }

        first.reentrantLock.lock();
        second.reentrantLock.lock();
        try {
            location.getAnimals().get(this.getClass()).remove(this);
            target.getAnimals()
                    .computeIfAbsent(this.getClass(), k -> new ArrayList<>())
                    .add(this);

            this.setMapPosition(newX, newY);
        } finally {
            second.reentrantLock.unlock();
            first.reentrantLock.unlock();

        }
    }

    public void eat(Eatable meal) {
        if (!(meal instanceof Animal)) return;
        Animal nAnimal = (Animal) meal;
        double mealWeight = nAnimal.getWeight();

        currentKillosOfMeal = Math.min(
                killosOfMealToSatisfaction,
                currentKillosOfMeal + mealWeight
        );
    }

    public double getCurrentKillosOfMeal() {
        return currentKillosOfMeal;
    }

    public Map<Class<? extends Eatable>, Integer> getDiet() {
        return diet;
    }

    public Class<? extends Eatable> foundMeal() {
        int count = Randomizer.generateNum(diet.size());
        int i = 0;
        for (Class<? extends Eatable> aClass : diet.keySet()) {
            if (i == count) {
                return aClass;
            } else {
                i++;
            }
        }
        return diet.keySet().stream().findFirst().get();
    }

    public Integer getMapPositionX() {
        return mapPositionX;
    }

    public Integer getMapPositionY() {
        return mapPositionY;
    }

    public double getWeight() {
        return weight;
    }

    public boolean isAlive() {
        return currentKillosOfMeal > 0;
    }

}

