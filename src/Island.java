import Util.Animal;
import service.Settings;

import java.io.Serializable;

public class Island {
    private Location[][] locations = new Location[Settings.x][Settings.y];



    private class Location{
        private Animal[] animals = new Animal[Settings.maxEmountOfAnimalsInOneCell];


        public void generateAnimals(){

        }


    }

    public void createNewIsland(){
        for (int i = 0; i < 100; i++) {
            locations[i] = new Location();
            for (int j = 0; j < 20; j++) {
                locations[i].animals[j] =
            }
        }
    }

    private void createNewLocation(){

    }

}
