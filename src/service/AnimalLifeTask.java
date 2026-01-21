package service;

import entity.Animal;
import entity.Eatable;
import entity.Plant;
import entity.animal.predator.Wolf;
import util.AnimalFactory;
import util.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;


public class AnimalLifeTask implements Runnable {


    private final Island island;
    private final Island.Location location;


    public AnimalLifeTask(Island.Location location, Island island) {
        this.location = location;
        this.island = island;
    }


    @Override
    public void run() {
        try {
            location.reentrantLock.lock();
            try {
                for (Class<? extends Eatable> listOfAnimal : Settings.listOfAnimals) {
                    List<Eatable> animals = new ArrayList<>(location.getAnimals().get(listOfAnimal));
                    for (Eatable animal : animals) {
                        processAnimal(animal);
                    }
                }
            } finally {
                location.reentrantLock.unlock();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processAnimal(Eatable newAnimal) {
        if (newAnimal instanceof Plant) {
            location.getAnimals().get(Plant.class).add(new Plant(999,999));
        } else {
            Animal animal = (Animal) newAnimal;
            if (!animal.isAlive()) {
                location.getAnimals().get(animal.getClass()).remove(animal);
            } else {
                if (animal.getMaxCellsByMove() == 0) return;
                //animal.move(island, location);


            }


        }

    }

    private void busy(){
        for (int i = 0; i < 50000; i++) {
            Math.sqrt(i);
        }
    }



}