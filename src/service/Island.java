package service;

import entity.Animal;
import entity.Eatable;
import entity.Plant;
import util.AnimalFactory;
import util.Randomizer;
import util.Settings;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Island {
    Random rand = new Random();
    private static Location[][] locations = new Location[Settings.MAP_SIZE_X][Settings.MAP_SIZE_Y];


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
            boolean locked = false;
            try {
                locked = reentrantLock.tryLock(100, TimeUnit.MILLISECONDS);
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
            } catch (InterruptedException e) {
                System.out.println("Поток прервали на этапе кормешки!");
            } finally {
                if (locked) {
                    reentrantLock.unlock();
                }
            }
        }

        public void move(int x, int y, Location[][] locations) {

            Map<Class<? extends Eatable>, ArrayList<Eatable>> map = getAnimals();

            for (var entry : map.entrySet()) {

                List<Eatable> snapshot = new ArrayList<>(entry.getValue());

                for (Eatable e : snapshot) {

                    if (!(e instanceof Animal animal)) continue;
                    if (!animal.isAlive()) continue;
                    if (animal.getMaxCellsByMove() == 0) continue;

                    int oldX = animal.getMapPositionX();
                    int oldY = animal.getMapPositionY();

                    animal.move(); // меняет координаты внутри Animal

                    int newX = animal.getMapPositionX();
                    int newY = animal.getMapPositionY();

                    if (oldX == newX && oldY == newY) continue;

                    if (!Island.isValidPosition(newX, newY, animal.getClass())) {
                        animal.setMapPosition(oldX, oldY);
                        continue;
                    }

                    Location from = locations[oldX][oldY];
                    Location to   = locations[newX][newY];

                    if (!lockBoth(from, to)) {
                        animal.setMapPosition(oldX, oldY);
                        continue;
                    }

                    try {
                        from.getAnimals().get(animal.getClass()).remove(animal);
                        to.getAnimals()
                                .computeIfAbsent(animal.getClass(), k -> new ArrayList<>())
                                .add(animal);
                    } finally {
                        unlockBoth(from, to);
                    }
                }
            }
        }
        private boolean lockBoth(Location a, Location b) {
            Location first = System.identityHashCode(a) < System.identityHashCode(b) ? a : b;
            Location second = first == a ? b : a;

            first.reentrantLock.lock();
            second.reentrantLock.lock();
            return true;
        }

        private void unlockBoth(Location a, Location b) {
            a.reentrantLock.unlock();
            b.reentrantLock.unlock();
        }


    }

    public void createNewIsland() {
        for (int i = 0; i < Settings.MAP_SIZE_X; i++) {
            for (int j = 0; j < Settings.MAP_SIZE_Y; j++) {
                locations[i][j] = new Location();
                try {
                    locations[i][j].generateAnimals(i, j);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Location[][] getLocations() {
        return locations;
    }

    public static boolean isValidPosition(int newX, int newY, Class<? extends Eatable> animal) {
        if (newX < 0 || newX >= Settings.MAP_SIZE_X ||
                newY < 0 || newY >= Settings.MAP_SIZE_Y) {
            return false;
        }
        Location loc = locations[newX][newY];
        loc.reentrantLock.lock();
        try {
            int count = loc.animals.get(animal).size();
            int max = animal.getDeclaredField("maxAmountInOneCell").getInt(null);
            return count < max;
        } catch (Exception e) {
            return false;
        } finally {
            loc.reentrantLock.unlock();
        }
    }
}
