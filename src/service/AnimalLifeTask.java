package service;

import entity.Animal;
import entity.Eatable;
import util.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class AnimalLifeTask implements Runnable {
    private final Island island;
    private final Island.Location[][] locations;
    private final int MAP_SIZE_X = Settings.MAP_SIZE_X;
    private final int MAP_SIZE_Y = Settings.MAP_SIZE_Y;

    public AnimalLifeTask(Island island) {
        this.island = island;
        locations = island.getLocations();
    }

    @Override
    public void run() {
        for (int i = 0; i < MAP_SIZE_X; i++) {
            for (int j = 0; j < MAP_SIZE_Y; j++) {
                processLocation(i, j);
            }
        }
    }

    private void processLocation(int x, int y) {
        boolean locked = false;

        try {

            locked = locations[x][y].reentrantLock.tryLock(100, TimeUnit.MILLISECONDS);

            List<Animal> animals = new ArrayList<>();
            for (Eatable entity : locations[x][y].getAnimals()) {
                if (entity instanceof Animal) {
                    animals.add((Animal) entity);
                }
            }
            animals.forEach(animal -> processAnimal(animal, x, y));

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("Ошибка в клетке [" + x + "][" + y + "]: " + e.getMessage());
        } finally {
            if (locked) {
                locations[x][y].reentrantLock.unlock();
            }
        }
    }

    private void processAnimal(Animal animal, int currentX, int currentY) {

        int oldX = animal.getMapPositionX();
        int oldY = animal.getMapPositionY();


        //locations[oldX][oldY].getAnimals().add(AnimalFactory.createNewAnimal(oldX,oldY, Wolf.class));
        animal.move();

        int newX = animal.getMapPositionX();
        int newY = animal.getMapPositionY();
        if ((newX != oldX || newY != oldY) &&
                (newX != currentX || newY != currentY)) {
            if (lockBothLocations(currentX, currentY, newX, newY)) {
                try {
                    locations[currentX][currentY].getAnimals().remove(animal);
                    locations[newX][newY].getAnimals().add(animal);
                } finally {
                    unlockBothLocations(currentX, currentY, newX, newY);
                }
            }
        }
    }


    private boolean lockBothLocations(int x1, int y1, int x2, int y2) {

        if (x1 < x2 || (x1 == x2 && y1 < y2)) {

            try {
                boolean lock1 = locations[x1][y1].reentrantLock.tryLock(50, TimeUnit.MILLISECONDS);
                if (!lock1) return false;

                boolean lock2 = locations[x2][y2].reentrantLock.tryLock(50, TimeUnit.MILLISECONDS);
                if (!lock2) {
                    locations[x1][y1].reentrantLock.unlock();
                    return false;
                }
                return true;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        } else {

            try {
                boolean lock2 = locations[x2][y2].reentrantLock.tryLock(50, TimeUnit.MILLISECONDS);
                if (!lock2) return false;

                boolean lock1 = locations[x1][y1].reentrantLock.tryLock(50, TimeUnit.MILLISECONDS);
                if (!lock1) {
                    locations[x2][y2].reentrantLock.unlock();
                    return false;
                }
                return true;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
    }


    private void unlockBothLocations(int x1, int y1, int x2, int y2) {
        if (x1 < x2 || (x1 == x2 && y1 < y2)) {
            locations[x2][y2].reentrantLock.unlock();
            locations[x1][y1].reentrantLock.unlock();
        } else {
            locations[x1][y1].reentrantLock.unlock();
            locations[x2][y2].reentrantLock.unlock();
        }
    }
}