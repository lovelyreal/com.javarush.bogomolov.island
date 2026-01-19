package service;
import entity.Eatable;
import entity.Plant;
import util.AnimalFactory;
import util.Settings;
import java.lang.reflect.Field;
import java.util.*;
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

                        int newborns = current / 2;
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


}
