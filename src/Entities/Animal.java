package Entities;

import Util.*;
import service.Island;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Animal implements Eatable {
    protected int maxAmountInOneCell;

    protected ReentrantLock reentrantLock = new ReentrantLock(true);
    protected Integer x;
    protected Integer y;
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

        public int getDx() {
            return dx;
        }

        public int getDy() {
            return dy;
        }
    }


    public Integer getMaxCellsByMove() {
        return maxCellsByMove;
    }


    public void move() {
        try {
            if (reentrantLock.tryLock(100, TimeUnit.MILLISECONDS)) {

                try {
                    if(maxCellsByMove != 0)
                    {
                        int steps = Randomizer.generateNum(maxCellsByMove);
                        Direction direction = Direction.random();
                        int newX = x + direction.dx * steps;
                        int newY = y + direction.dy * steps;

                        if (Island.isValidPosition(newX, newY, this.getClass())) {
                            this.x = newX;
                            this.y = newY;
                        }
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    System.out.println("Ошибка при проверке позиции: " + e.getMessage());
                } finally {
                    reentrantLock.unlock();
                }
            } else {
                System.out.println("Не удалось заблокировать животное для перемещения");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Поток прерван: " + e.getMessage());
        }
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }
}

