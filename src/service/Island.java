package service;

import entity.Animal;
import entity.animal.herbivore.*;
import entity.animal.predator.*;
import entity.Plant;
import entity.Eatable;
import util.AnimalFactory;
import util.Settings;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Island {
    Random rand = new Random();
    private static Location[][] locations = new Location[Settings.MAP_SIZE_X][Settings.MAP_SIZE_Y];


    public class Location {
        public final ReentrantLock reentrantLock = new ReentrantLock();
        private Map<Class<? extends Eatable>, Integer> amountOfEntitiesInOneLocation;
        private Map<Class<? extends Eatable>, ArrayList<Eatable>> animals = new HashMap<>();
        //private List<Eatable> animals = new ArrayList<>(Settings.maxAmountOfAnimalsInOneCell);


        public void generateAnimals(int x, int y) throws Exception {

            for (Class<? extends Eatable> listOfAnimal : Settings.listOfAnimals) {
                animals.put(listOfAnimal, new ArrayList<>());
            }


            for (Class<? extends Eatable> animal : Settings.listOfAnimals) {
                Field field = animal.getDeclaredField("maxAmountInOneCell");
                field.setAccessible(true);
                int numSpecies = rand.nextInt(field.getInt(null));
                ArrayList<Eatable> a = new ArrayList<>();
                for (int i = 0; i < numSpecies; i++) {
                    Eatable nAnimal = AnimalFactory.createNewAnimal(x, y, animal);
                    a.add(nAnimal);
                }
                animals.put(animal,a);
            }
        }

        public Map<Class<? extends Eatable>, ArrayList<Eatable>> getAnimals() {
            return animals;
        }

//        public void breedAnimals() {
//            int x = 0;
//            int y = 0;
//            try {
//                if (reentrantLock.tryLock(100, TimeUnit.MILLISECONDS)) {
//
//                    for (Class<? extends Eatable> animalClass : Settings.listOfAnimals) {
//                        List<Eatable> list = animals.stream().filter(animal -> animal.getClass() == animalClass).toList();
//                        if (list.get(0) instanceof Animal && list.get(0).getClass() != Caterpillar.class) {
//                            Animal i1 = (Animal) list.get(0);
//                            x = i1.getMapPositionX();
//                            y = i1.getMapPositionY();
//
//                            long countAnimals = animalCountInCurrentLocations(animalClass, this);
//                            if (countAnimals > 2) {
//                                int breedReadyAnimals = (int) countAnimals / 2;
//                                try {
//                                    Field animalAmount = animalClass.getDeclaredField("maxAmountInOneCell");
//                                    animalAmount.setAccessible(true);
//                                    for (int i = 0; i < breedReadyAnimals; i++) {
//                                        if (countAnimals < animalAmount.getInt(null)) {
//                                            animals.add(AnimalFactory.createNewAnimal(x, y, animalClass));
//                                        }
//                                    }
//
//                                } catch (IllegalAccessException e) {
//                                    System.out.println("Доступ запрещен!");
//                                } catch (NoSuchFieldException e) {
//                                    System.out.println("такого поля нет");
//                                }
//
//                            }
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//            finally {
//                reentrantLock.unlock();
//            }
//        }
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


//    public long animalCountInCurrentLocations(Class<? extends Eatable> animalClass, Location location) {
//        long result = 0;
//        if (location.animals != null) {
//            result += location.animals.stream()
//                    .filter(Objects::nonNull)
//                    .filter(animalClass::isInstance)
//                    .count();
//        }
//        return result;
//    }

    private static long animalCounterByXY(int x, int y, Class<? extends Eatable> animalClass) {
        return locations[x][y].animals.get(animalClass).size();
    }

    public Location[][] getLocations() {
        return locations;
    }

    public static boolean isValidPosition(int newX, int newY, Class<? extends Eatable> animal) throws NoSuchFieldException, IllegalAccessException {
        if (newX < 0 || newX >= Settings.MAP_SIZE_X || newY < 0 || newY >= Settings.MAP_SIZE_Y) {
            return false;
        }
        if (animalCounterByXY(newX, newY, animal) < animal.getDeclaredField("maxAmountInOneCell").getInt(null)) {
            return true;
        } else {
            return false;
        }
    }
}
