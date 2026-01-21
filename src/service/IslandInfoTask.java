package service;

import entity.Eatable;
import util.Settings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class IslandInfoTask implements Runnable {
    private final Map<Class<? extends Eatable>, Integer> animalCount = new ConcurrentHashMap<>();
    private Integer islandDay;
    private final Island.Location[][] locations;
    public IslandInfoTask(Island island, Integer day) {
        locations = island.getLocations();
        islandDay = day;
    }

    @Override
    public void run() {
        Integer result = 0;
        System.out.print("Day:" + islandDay);
        System.out.println();

        for (Class<? extends Eatable> listOfAnimal : Settings.listOfAnimals) {
            animalCount.put(listOfAnimal, 0);
        }
        for (int i = 0; i < Settings.MAP_SIZE_X; i++) {
            for (int j = 0; j < Settings.MAP_SIZE_Y; j++) {

                boolean locked = false;
                try {
                    locked = locations[i][j].reentrantLock.tryLock();
                    if (locked) {
                        for (Class<? extends Eatable> aClass : locations[i][j].getAnimals().keySet()) {
                            Integer i1 = animalCount.get(aClass);
                            i1 += locations[i][j].getAnimals().get(aClass).size();
                            animalCount.put(aClass,i1);
                        }

                    }
                } finally {
                    if (locked) {
                        locations[i][j].reentrantLock.unlock();
                    }
                }
            }
        }
        for (Map.Entry<Class<? extends Eatable>, Integer> classIntegerEntry : animalCount.entrySet()) {
            System.out.print(classIntegerEntry.getKey().getSimpleName() + " -> " + classIntegerEntry.getValue() + " ");

        }
        islandDay++;
        System.out.println();
    }


}