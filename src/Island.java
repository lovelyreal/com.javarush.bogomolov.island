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
            for (int j = 0; j < 20; j++) {
                locations[i][j] = new Location();
                locations[i][j].generateAnimals();
            }
        }
    }

    private void createNewLocation(){

    }

}
