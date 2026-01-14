package service;

import Util.AnimalFactory;
import Util.Eatable;
import Util.Settings;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class Island {
    Random rand = new Random();
    private static Location[][] locations = new Location[Settings.x][Settings.y];


    public class Location {
        public ReentrantLock reentrantLock = new ReentrantLock(true);
        private Map<Class<? extends Eatable>, Integer> amountOfEntitiesInOneLocation;
        private List<Eatable> animals = new ArrayList<>(Settings.maxAmountOfAnimalsInOneCell);


        public void generateAnimals(int x, int y) throws Exception {
            for (Class<? extends Eatable> animal : Settings.listOfAnimals) {
                Field field = animal.getDeclaredField("maxAmountInOneCell");
                field.setAccessible(true);
                int numSpecies = rand.nextInt(field.getInt(null)) + 1;
                Constructor<? extends Eatable> constructor = animal.getDeclaredConstructor(int.class, int.class);
                for (int i = 0; i < numSpecies; i++) {
                    Eatable nAnimal = constructor.newInstance(x, y);
                    animals.add(nAnimal);
                }
            }

        }

        public List<Eatable> getAnimals() {
            return animals;
        }
    }

    public void createNewIsland() {
        for (int i = 0; i < Settings.x; i++) {
            for (int j = 0; j < Settings.y; j++) {
                locations[i][j] = new Location();
                try {
                    locations[i][j].generateAnimals(i, j);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void info() {
        for (int i = 0; i < Settings.x; i++) {
            long animalCounter = 0;
            int space = 0;
            System.out.println("Location" + "{" + i + "}" + "START");
            Location[] location = locations[i];
            for (Class<? extends Eatable> listOfAnimal : Settings.listOfAnimals) {
                try {
                    if (space == 5) {
                        System.out.println();
                        space = 0;
                    }
                    System.out.print(listOfAnimal.getDeclaredConstructor(int.class, int.class)
                            .newInstance(999, 999).toString() + " -> " +
                            animalCountInLocations(listOfAnimal, location) + " ");
                    space++;
                } catch (InstantiationException | InvocationTargetException | IllegalAccessException |
                         NoSuchMethodException e) {
                    System.out.println("Ошибка!!!");
                }
            }

            System.out.println();
            System.out.println("Location" + "{" + i + "}" + "END");



        }
    }


    public long animalCountInLocations(Class<? extends Eatable> animalClass, Location[] locations) {
        long result = 0;
        for (Location location : locations) {
            if (location != null && location.animals != null) {
                result += location.animals.stream()
                        .filter(Objects::nonNull)
                        .filter(animalClass::isInstance)
                        .count();
            }
        }
        return result;
    }

    private static long animalCounterByXY(int x, int y, Class<? extends Eatable> animalClass){
        long count = locations[x][y].animals.stream()
                .filter(Objects::nonNull)
                .filter(animalClass::isInstance)
                .count();
        return count;
    }

    public Location[][] getLocations() {
        return locations;
    }

    public static boolean isValidPosition(int newX, int newY, Class<? extends Eatable> animal) throws NoSuchFieldException, IllegalAccessException {
        if (newX < 0 || newX >= Settings.x || newY < 0 || newY >= Settings.y) {
            return false;
        }
        if(animalCounterByXY(newX, newY, animal) < animal.getDeclaredField("maxAmountInOneCell").getInt(null)){
            return true;
        } else{
            return false;
        }
    }
    public static void addAnimal(int x, int y, Eatable animal){
        locations[x][y].animals.add(animal);
    }
    public static void removeAnimal(int x, int y, Eatable animal){
        locations[x][y].animals.remove(animal);
    }
}
