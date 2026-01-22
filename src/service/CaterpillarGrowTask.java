package service;

import entity.Eatable;
import entity.Plant;
import entity.animal.herbivore.Caterpillar;

import java.util.ArrayList;
import java.util.Map;

public class CaterpillarGrowTask implements Runnable{
    @Override
    public void run() {
        Island instance = Island.getInstance();
        for (Island.Location[] location : instance.getLocations()) {
            for (Island.Location innerLocation : location) {
                try {
                    innerLocation.reentrantLock.lock();
                    Map<Class<? extends Eatable>, ArrayList<Eatable>> eatables = innerLocation.getAnimals();
                    for (int i = 0; i < 100; i++) {
                        eatables.get(Caterpillar.class).add(new Caterpillar(9, 9));
                    }
                } finally {
                    innerLocation.reentrantLock.unlock();
                }
            }
        }
    }
}
