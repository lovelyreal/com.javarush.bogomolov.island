package entity;

import util.*;
import service.Island;

import java.util.ArrayList;
import java.util.List;
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


    public void move(Island island, Island.Location current) {

        Direction dir = Direction.random();
        if (dir == null) return;

        int newX = mapPositionX + dir.dx;
        int newY = mapPositionY + dir.dy;

        if (newX < 0 || newX >= Settings.MAP_SIZE_X ||
        newY < 0 || newY >= Settings.MAP_SIZE_Y) {
            return;
        }

        Island.Location target = island.getLocations()[newX][newY];
        if (target == current) return;

        Island.Location first;
        Island.Location second;

        if (System.identityHashCode(current) < System.identityHashCode(target)) {
            first = current;
            second = target;
        } else {
            first = target;
            second = current;
        }

        boolean firstLocked = false;
        boolean secondLocked = false;

        try {
            firstLocked = first.reentrantLock.tryLock(50, TimeUnit.MILLISECONDS);
            if (!firstLocked) return;

            secondLocked = second.reentrantLock.tryLock(50, TimeUnit.MILLISECONDS);
            if (!secondLocked) return;

            // 🔥 ВСЕ ПРОВЕРКИ ТОЛЬКО ЗДЕСЬ
            if (!Island.isValidPositionUnsafe(target, this.getClass())) {
                return;
            }

            current.getAnimals().get(this.getClass()).remove(this);
            target.getAnimals()
                    .computeIfAbsent(this.getClass(), k -> new ArrayList<>())
                    .add(this);

            this.setMapPosition(newX, newY);
            this.currentKillosOfMeal *= 0.9;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (secondLocked) second.reentrantLock.unlock();
            if (firstLocked) first.reentrantLock.unlock();
        }
    }

    public void eat(Eatable eatable) {
        if (eatable != null) {
            Animal m = (Animal) eatable;
            currentKillosOfMeal += m.weight;
        }
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

    public double getKillosOfMealToSatisfaction() {
        return killosOfMealToSatisfaction;
    }
}

