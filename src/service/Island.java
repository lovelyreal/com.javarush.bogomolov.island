package service;

import entity.Animal;
import entity.Eatable;
import entity.Plant;
import entity.animal.herbivore.Caterpillar;
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

        public void breed() {
                for (Class<? extends Eatable> species : animals.keySet()) {
                    if (species != Plant.class && species != Caterpillar.class) {
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
                        int newborns = current / 2;
                        int availableSpace = max - current;

                        int toCreate = Math.min(newborns, availableSpace);

                        for (int i = 0; i < toCreate; i++) {
                            Eatable baby = AnimalFactory.createNewAnimal(999,999,species);
                            list.add(baby);
                        }
                    }
                }
        }
        public void eatingProccess() {
            for (Class<? extends Eatable> species : animals.keySet()) {
                List<Eatable> spec = new ArrayList<>(animals.get(species));
                for (Eatable eatable : spec) {
                    if(eatable instanceof Animal animal) {
                        if (animal.getCurrentKillosOfMeal() < animal.getKillosOfMealToSatisfaction()) {
                            //System.out.println("!!!!!!!!!!!!!!!!!!!!");
                            for (Class<? extends Eatable> aClass : animal.getDiet().keySet()) {
                                ArrayList<Eatable> eatables = new ArrayList<>(animals.get(aClass));
                                for (Eatable eatable1 : eatables) {
                                    if (Randomizer.generateNum(100) > animal.getDiet().get(aClass)) {
                                        animal.eat(eatable1);
                                        animals.get(aClass).remove(eatable1);

                                    }
                                }

                            }
                        }
                    }
                }
            }
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

    public static boolean isValidPositionUnsafe(
            Island.Location loc,
            Class<? extends Eatable> animalClass
    ) {
        try {
            int count = loc.getAnimals().get(animalClass).size();
            int max = animalClass
                    .getDeclaredField("maxAmountInOneCell")
                    .getInt(null);
            return count < max;
        } catch (Exception e) {
            return false;
        }
    }
}
