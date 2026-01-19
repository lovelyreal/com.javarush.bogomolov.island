package entity;

import util.*;
import service.Island;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Animal implements Eatable {
    protected static int maxAmountInOneCell;

    protected ReentrantLock reentrantLock = new ReentrantLock(true);
    protected Integer mapPositionX;
    protected Integer mapPositionY;
    protected double weight;
    protected Integer maxCellsByMove;
    protected double killosOfMealToSatisfaction;
    protected double currentKillosOfMeal = killosOfMealToSatisfaction / 2d;
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


    public void move() {
        if (maxCellsByMove == 0) return;

        boolean locked = false;
        try {
            locked = reentrantLock.tryLock(100, TimeUnit.MILLISECONDS);
            if (!locked) return;

            int steps = Randomizer.generateNum(maxCellsByMove);
            Direction direction = Direction.random();

            int newX = mapPositionX + direction.dx * steps;
            int newY = mapPositionY + direction.dy * steps;

            if (Island.isValidPosition(newX, newY, this.getClass())) {
                this.mapPositionX = newX;
                this.mapPositionY = newY;
                this.currentKillosOfMeal -= 2;
            }
        } catch (InterruptedException _) {
            Thread.currentThread().interrupt();
        } finally {
            if (locked) {
                reentrantLock.unlock();
            }
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

    public Class<? extends Eatable> foundMeal(){
        int count = Randomizer.generateNum(diet.size());
        int i = 0;
        for (Class<? extends Eatable> aClass : diet.keySet()) {
            if(i == count){
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
}

