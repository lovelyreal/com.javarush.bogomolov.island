package service;

import entity.Animal;
import entity.Eatable;
import util.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class AnimalBreedTask implements Runnable{

    private final Island.Location[][] locations;
    private final int MAP_SIZE_X = Settings.MAP_SIZE_X;
    private final int MAP_SIZE_Y = Settings.MAP_SIZE_Y;

    public AnimalBreedTask(Island island) {
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
            System.err.println("💥 КРИТИЧЕСКАЯ ОШИБКА В AnimalMoveTask");
            t.printStackTrace();
        }
    }
    private void processLocation(int x, int y) throws InterruptedException {
        boolean locked = locations[x][y].reentrantLock.tryLock(100, TimeUnit.MILLISECONDS);
        if (!locked) {
            return;
        }
        try {
            locations[x][y].breed(x, y);
        } finally {
            locations[x][y].reentrantLock.unlock();
        }
    }
}
