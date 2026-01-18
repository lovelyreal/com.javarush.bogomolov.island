package service;

import entity.Animal;
import entity.Eatable;
import util.AnimalFactory;
import util.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
                try {
                    processLocation(i, j);
                } catch (InterruptedException e) {
                    System.out.println("Ошибочка!");
                }
            }
        }
    }

    private void processLocation(int x, int y) throws InterruptedException {

        try {

            int timeout = locations[x][y].getAnimals().size() > 5 ? 50 : 10;
            locations[x][y].reentrantLock.tryLock(timeout, TimeUnit.MILLISECONDS);
            Map<Class<? extends Eatable>, ArrayList<Eatable>> currentMap = locations[x][y].getAnimals();
            for (Class<? extends Eatable> aClass : currentMap.keySet()) {
                currentMap.get(aClass).stream()
                        .filter(Objects::nonNull)
                        .filter(u -> u instanceof Animal)
                        .map(u -> (Animal) u).forEach(u -> processAnimal(u,x,y));
            }


//            List<Animal> animals = new ArrayList<>();
//            for (Eatable entity : locations[x][y].getAnimals(;) {
//                if (entity instanceof Animal) {
//                    animals.add((Animal) entity);
//                }
//            }
//            animals.forEach(t -> processAnimal(t,x,y));



        }  finally {
                locations[x][y].reentrantLock.unlock();

        }
    }

    private void processAnimal(Animal animal, int currentX, int currentY) {
        if(animal.getMaxCellsByMove() == 0){return;}

        int oldX = animal.getMapPositionX();
        int oldY = animal.getMapPositionY();

        animal.move();

        int newX = animal.getMapPositionX();
        int newY = animal.getMapPositionY();
        if (newX == oldX && newY == oldY) {
            return;
        }
        if (newX != currentX || newY != currentY) {
            if (lockBothLocations(currentX, currentY, newX, newY)) {
                try {
                    locations[currentX][currentY].getAnimals().get(animal.getClass()).removeFirst();
                    locations[newX][newY].getAnimals().get(animal.getClass()).add(AnimalFactory.createNewAnimal(newX,newY,animal.getClass()));
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