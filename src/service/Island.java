package service;

import Entities.Animals.Herbivore.*;
import Entities.Animals.Predators.*;
import Entities.Plant;
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
        public final ReentrantLock reentrantLock = new ReentrantLock();
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
            for (int j = 0; j < Settings.y; j++) {
//            long animalCounter = 0;
//            int space = 0;
//            System.out.println("Location" + "{" + i + "}" + "START");
//            Location[] location = locations[i];
//            for (Class<? extends Eatable> listOfAnimal : Settings.listOfAnimals) {
//                try {
//                    if (space == 5) {
//                        System.out.println();
//                        space = 0;
//                    }
//                    System.out.print(listOfAnimal.getDeclaredConstructor(int.class, int.class)
//                            .newInstance(999, 999).toString() + " -> " +
//                            animalCountInLocations(listOfAnimal, location) + " ");
//                    space++;
//                } catch (InstantiationException | InvocationTargetException | IllegalAccessException |
//                         NoSuchMethodException e) {
//                    System.out.println("Ошибка!!!");
//                }
//            }
//
//            System.out.println();
//            System.out.println("Location" + "{" + i + "}" + "END");

                System.out.println("Location" + "{" + i + " ; " + j + "}" + "START");
                printAnimalCount("\uD83D\uDC3A", Wolf.class, this.locations[i][j].animals);
                printAnimalCount("\uD83E\uDD8A", Fox.class, this.locations[i][j].animals);
                printAnimalCount("\uD83E\uDD85", Eagle.class, this.locations[i][j].animals);
                printAnimalCount("\uD83D\uDC0D", BoaConstrictor.class, this.locations[i][j].animals);
                printAnimalCount("\uD83D\uDC3B", Bear.class, this.locations[i][j].animals);
                System.out.println();
                printAnimalCount("\uD83D\uDC11", Sheep.class, this.locations[i][j].animals);
                printAnimalCount("\uD83D\uDC07", Rabbit.class, this.locations[i][j].animals);
                printAnimalCount("\uD83D\uDC01", Mouse.class, this.locations[i][j].animals);
                printAnimalCount("\uD83D\uDC34", Horse.class, this.locations[i][j].animals);
                printAnimalCount("\uD83D\uDC10", Goat.class, this.locations[i][j].animals);
                System.out.println();
                printAnimalCount("\uD83E\uDD86", Duck.class, this.locations[i][j].animals);
                printAnimalCount("\uD83E\uDD8C", Deer.class, this.locations[i][j].animals);
                printAnimalCount("\uD83D\uDC1B", Caterpillar.class, this.locations[i][j].animals);
                printAnimalCount("\uD83D\uDC03", Buffalo.class, this.locations[i][j].animals);
                printAnimalCount("\uD83D\uDC17", Boar.class, this.locations[i][j].animals);
                printAnimalCount("\uD83C\uDF3F", Plant.class, this.locations[i][j].animals);
                System.out.println();
                System.out.println("Location" + "{" + i + " ; " + j + "}" + "END");
                System.out.println("/".repeat(10));

            }
        }
    }

    private void printAnimalCount(String emoji, Class<?> animalClass, List<Eatable> animals) {
        long count = animals.stream()
                .filter(animalClass::isInstance)
                .count();
        System.out.print(emoji + " -> " + count + ';');
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

    private static long animalCounterByXY(int x, int y, Class<? extends Eatable> animalClass) {
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
        if (animalCounterByXY(newX, newY, animal) < animal.getDeclaredField("maxAmountInOneCell").getInt(null)) {
            return true;
        } else {
            return false;
        }
    }

    public static void addAnimal(int x, int y, Eatable animal) {
        locations[x][y].animals.add(animal);
    }

    public static void removeAnimal(int x, int y, Eatable animal) {
        locations[x][y].animals.remove(animal);
    }
}
