package service;

import entity.Animal;
import entity.Eatable;
import entity.animal.predator.Wolf;
import util.AnimalFactory;
import util.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class AnimalLifeTask implements Runnable {
    private final Island.Location[][] locations;
    private final int MAP_SIZE_X = Settings.MAP_SIZE_X;
    private final int MAP_SIZE_Y = Settings.MAP_SIZE_Y;

    public AnimalLifeTask(Island island) {
        locations = island.getLocations();
    }

    @Override
    public void run() {
        try {


            for (int i = 0; i < MAP_SIZE_X; i++) {
                for (int j = 0; j < MAP_SIZE_Y; j++) {
                    processLocation(i, j);
                }
            }
        } catch (Throwable t) {
            System.err.println("💥 КРИТИЧЕСКАЯ ОШИБКА В AnimalLifeTask");
            t.printStackTrace();
        }
    }

    private void processLocation(int x, int y) throws InterruptedException {
        boolean locked = locations[x][y].reentrantLock.tryLock(100, TimeUnit.MILLISECONDS);
        if (!locked) {
            return;
        }

        try {
            Map<Class<? extends Eatable>, ArrayList<Eatable>> currentMap =
                    locations[x][y].getAnimals();

            for (Class<? extends Eatable> aClass : currentMap.keySet()) {
                List<Eatable> snapshot = new ArrayList<>(currentMap.get(aClass));

                snapshot.stream()
                        .filter(Objects::nonNull)
                        .filter(Animal.class::isInstance)
                        .map(Animal.class::cast)
                        .forEach(u -> processAnimal(u, x, y));
            }
            locations[x][y].eatingProcess(x, y);
            locations[x][y].breed(x, y);
        } finally {
            locations[x][y].reentrantLock.unlock();
        }
    }

    private void processAnimal(Animal animal, int currentX, int currentY) {
        if (!animal.isAlive()) {
            locations[currentX][currentY].getAnimals().get(animal.getClass()).remove(animal);

        }
        if (animal.getCurrentKillosOfMeal() >= 0) {

            if (animal.getMaxCellsByMove() == 0) return;

            int oldX = animal.getMapPositionX();
            int oldY = animal.getMapPositionY();

            animal.move();


            int newX = animal.getMapPositionX();
            int newY = animal.getMapPositionY();

            if (newX == oldX && newY == oldY) return;

            if (!lockBothLocations(oldX, oldY, newX, newY)) {
                animal.setMapPosition(oldX, oldY);
                return;
            }

            try {
                List<Eatable> oldList =
                        locations[oldX][oldY].getAnimals().get(animal.getClass());

                if (oldList != null) {
                    oldList.remove(animal);
                }

                locations[newX][newY].getAnimals()
                        .computeIfAbsent(animal.getClass(), k -> new ArrayList<>())
                        .add(animal);

            } finally {
                unlockBothLocations(oldX, oldY, newX, newY);
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