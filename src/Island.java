import Animals.Herbivore.*;
import Animals.Predators.*;
import Util.Eatable;
import Util.Plant;
import service.Settings;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

public class Island {
    Random rand = new Random();
    private Location[][] locations = new Location[Settings.x][Settings.y];


    private class Location {
        private Map<Class<? extends Eatable>, Integer> amountOfEntitiesInOneLocation;
        private Eatable[] animals = new Eatable[Settings.maxAmountOfAnimalsInOneCell];
        int numAnimals = 0;


        public void generateAnimals(int x, int y) throws Exception {
            for (Class<? extends Eatable> animal : Settings.listOfAnimals) {
                Field field = animal.getDeclaredField("maxAmountInOneCell");
                field.setAccessible(true);
                int numSpecies = rand.nextInt(field.getInt(null)) + 1;
                Constructor<? extends Eatable> constructor = animal.getDeclaredConstructor(int.class, int.class);
                for (int i = 0; i < numSpecies; i++) {
                    Eatable nAnimal = constructor.newInstance(x, y);
                    animals[numAnimals] = nAnimal;
                    numAnimals++;
                }
            }

        }


    }

    public void createNewIsland() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 20; j++) {
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

    private void printAnimalCount(String emoji, Class<?> animalClass, Eatable[] animals) {
        long count = Arrays.stream(animals)
                .filter(animalClass::isInstance)
                .count();
        System.out.print(emoji + " -> " + count + ';');
    }

}
