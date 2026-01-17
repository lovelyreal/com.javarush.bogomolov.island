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
        if (maxCellsByMove != 0) {
            try {
                int steps = Randomizer.generateNum(maxCellsByMove);
                Direction direction = Direction.random();
                int newX = mapPositionX + direction.dx * steps;
                int newY = mapPositionY + direction.dy * steps;

                if (Island.isValidPosition(newX, newY, this.getClass())) {
                    this.mapPositionX = newX;
                    this.mapPositionY = newY;
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                System.out.println("Ошибка при проверке позиции: " + e.getMessage());
            }
        }
    }

    public Integer getMapPositionX() {
        return mapPositionX;
    }

    public Integer getMapPositionY() {
        return mapPositionY;
    }
}

