import Animals.Predators.Wolf;
import Util.Animal;
import service.Settings;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Random;

import java.io.Serializable;
import java.util.Set;

public class Island {
    Random rand = new Random();
    private Location[][] locations = new Location[Settings.x][Settings.y];



    private class Location{

        private Animal[] animals = new Animal[Settings.maxEmountOfAnimalsInOneCell];
        int numAnimals = 0;

        public void generateAnimals(int x, int y) throws Exception {
            for (Class<? extends Animal> animal : Settings.listOfAnimals) {
                Field field = animal.getDeclaredField("maxEmountInOneCell");
                field.setAccessible(true);
                int numSpecies = rand.nextInt(field.getInt(null))+1;
                Constructor<? extends Animal> constructor = animal.getDeclaredConstructor(int.class, int.class);
                for (int i = 0; i < numSpecies; i++) {
                    Animal nAnimal = constructor.newInstance(x,y);
                    animals[numAnimals] = nAnimal;
                    numAnimals++;
                }
            }

        }


    }

    public void createNewIsland(){
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

    public void info(){
        for (int i = 0; i < Settings.x; i++) {
            for (int j = 0; j < Settings.y; j++) {
                for (int k = 0; k < Settings.maxEmountOfAnimalsInOneCell; k++) {
                    if(this.locations[i][j].animals[k] != null){
                        System.out.println(this.locations[i][j].animals[k]);
                    }
                }
                System.out.println("Location end");
            }
        }
    }

}
