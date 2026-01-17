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
    private Integer islandDay = 0;
    private final Island.Location[][] locations;
    public IslandInfoTask(Island island) {
        locations = island.getLocations();
    }

    @Override
    public void run() {
        System.out.print("Day:" + islandDay);
        System.out.println();

        for (Class<? extends Eatable> listOfAnimal : Settings.listOfAnimals) {
            animalCount.put(listOfAnimal, 0);
        }
        for (int i = 0; i < Settings.MAP_SIZE_X; i++) {
            for (int j = 0; j < Settings.MAP_SIZE_Y; j++) {

                boolean locked = false;
                try {
                    locked = locations[i][j].reentrantLock.tryLock(500, TimeUnit.MILLISECONDS);
                    if (locked) {
                        List<? extends Class<? extends Eatable>> list =
                                locations[i][j].getAnimals()
                                        .stream()
                                        .map(Eatable::getClass)
                                        .toList();
                        for (Class<? extends Eatable> animalClass : list) {
                            animalCount.put(animalClass, animalCount.get(animalClass) + 1);
                        }
                    }
                } catch (InterruptedException e) {
                    System.out.println("Процесс сбора статистики прервали!!!");
                    Thread.currentThread().interrupt(); // Восстанавливаем флаг прерывания
                    return;
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