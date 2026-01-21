package service;

import entity.Animal;
import entity.Eatable;
import entity.Plant;
import util.AnimalFactory;
import util.Randomizer;
import util.Settings;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class Island {
    Random rand = new Random();
    private static Location[][] locations = new Location[Settings.MAP_SIZE_X][Settings.MAP_SIZE_Y];
    private static Island support = new Island();
    private static Island instance;

    public static void initialize() {
        if (instance == null)
            instance = support.createNewIsland();
        else
            throw new IllegalStateException("Island already initialized.");
    }

    public static synchronized Island getInstance() {
        if (instance == null)
            throw new IllegalStateException("Island not initialized.");
        else return instance;
    }


    public class Location {
        public final ReentrantLock reentrantLock = new ReentrantLock();
        private Map<Class<? extends Eatable>, ArrayList<Eatable>> animals;


        public void generateAnimals(int x, int y) throws Exception {
            animals = new HashMap<>();
            for (Class<? extends Eatable> animal : Settings.listOfAnimals) {
                Field field = animal.getDeclaredField("maxAmountInOneCell");
                field.setAccessible(true);
                int numSpecies = rand.nextInt(field.getInt(null));
                ArrayList<Eatable> a = new ArrayList<>();
                for (int i = 0; i < numSpecies; i++) {
                    Eatable nAnimal = AnimalFactory.createNewAnimal(x, y, animal);
                    a.add(nAnimal);
                }
                animals.put(animal, a);
            }
        }

        public Map<Class<? extends Eatable>, ArrayList<Eatable>> getAnimals() {
            return animals;
        }

        public void breed(int x, int y) {
            reentrantLock.lock();
            try {
                for (Class<? extends Eatable> species : animals.keySet()) {
                    if (species != Plant.class) {
                        List<Eatable> list = animals.get(species);
                        if (list == null || list.size() < 2) continue;
                        int current = list.size();
                        int max;
                        try {
                            Field f = species.getDeclaredField("maxAmountInOneCell");
                            f.setAccessible(true);
                            max = f.getInt(null);
                        } catch (Exception e) {
                            continue;
                        }
                        int newborns = current / 4;
                        int availableSpace = max - current;

                        int toCreate = Math.min(newborns, availableSpace);

                        for (int i = 0; i < toCreate; i++) {
                            Eatable baby = AnimalFactory.createNewAnimal(x, y, species);
                            list.add(baby);
                        }
                    }
                }
            } finally {
                reentrantLock.unlock();
            }
        }


        public void eatingProcess(int x, int y) {

            try {
                if (!reentrantLock.tryLock()) return;
                for (Class<? extends Eatable> aClass : animals.keySet()) {
                    if (aClass != Plant.class) {

                        List<Eatable> eatables = animals.get(aClass);
                        List<Animal> hunters = eatables.stream()
                                .filter(Animal.class::isInstance)
                                .map(Animal.class::cast)
                                .toList();
                        for (Animal hunter : hunters) {
                            Class<? extends Eatable> huntersMeal = hunter.foundMeal();
                            int percentOfSuccess = Randomizer.generateNum(100);
                            if (percentOfSuccess < hunter.getDiet().get(huntersMeal)) {
                                List<Eatable> meals = animals.get(huntersMeal);
                                if (meals == null) {
                                    return;
                                }
                                if (meals.size() > 1) {
                                    Eatable victim = meals.remove(0);
                                    hunter.eat(victim);
                                }
                            }


                        }
                    }
                }
            } finally {
                    reentrantLock.unlock();
            }
        }

        public boolean addCreature(Eatable eatable) {
            if (eatable instanceof Plant plant) {
                animals.get(Plant.class).add(new Plant(999, 999));
                return true;
            } else if (eatable instanceof Animal animal) {
                animals.get(eatable.getClass()).add(eatable);
                return true;
            }
            return false;
        }

        public void removeCreature(Eatable eatable) {
            animals.get(eatable.getClass()).remove(eatable);
        }
    }

    public Island createNewIsland() {
        Island newIsland = new Island();
        for (int i = 0; i < Settings.MAP_SIZE_X; i++) {
            for (int j = 0; j < Settings.MAP_SIZE_Y; j++) {
                newIsland.getLocations()[i][j] = new Location();
                try {
                    newIsland.getLocations()[i][j].generateAnimals(i, j);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return newIsland;
    }

    public Location[][] getLocations() {
        return locations;
    }

    public static boolean isValidPosition(int newX, int newY, Class<? extends Eatable> animal,Location loc
    ) {
        if (newX < 0 || newX >= Settings.MAP_SIZE_X ||
                newY < 0 || newY >= Settings.MAP_SIZE_Y) {
            return false;
        }

        int count = loc.getAnimals().get(animal).size();
        int max;
        try {
            max = animal.getDeclaredField("maxAmountInOneCell").getInt(null);
        } catch (Exception e) {
            return false;
        }
        return count < max;
    }
}
