package service;

import entity.Eatable;
import entity.Plant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlantGrowTask implements Runnable{
    @Override
    public void run() {
        Island instance = Island.getInstance();
        for (Island.Location[] location : instance.getLocations()) {
            for (Island.Location innerLocation : location) {
                try {
                    innerLocation.reentrantLock.lock();
                    Map<Class<? extends Eatable>, ArrayList<Eatable>> eatables = innerLocation.getAnimals();
                    for (int i = 0; i < 100; i++) {
                        eatables.get(Plant.class).add(new Plant(9, 9));
                    }
                } finally {
                    innerLocation.reentrantLock.unlock();
                }
            }
        }
    }
}
