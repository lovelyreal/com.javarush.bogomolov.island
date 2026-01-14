package service;

import Entities.Animal;
import Entities.Plant;
import Util.*;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class Task implements Runnable {

    Island island;
    Island.Location[][] locations;
    private int x;
    private int y;

    public Task(int x, int y, Island island) {
        this.island = island;
        locations = island.getLocations();
        this.x = x;
        this.y = y;
    }

    @Override
    public void run() {
        try {
            if (locations[x][y].reentrantLock.tryLock(100, TimeUnit.MILLISECONDS)) {
                List<Eatable> animals = locations[x][y].getAnimals();
                for (Eatable entity : animals) {
                    if (entity instanceof Animal) {
                        Animal animal = (Animal) entity;
                        animal.move();
                        System.out.println(animal.toString() + "переместился!");
                    }
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            locations[x][y].reentrantLock.unlock();
        }
        }
    }
