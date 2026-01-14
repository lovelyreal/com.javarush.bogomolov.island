package Util;

import com.sun.jdi.event.ThreadDeathEvent;
import service.*;

public class AnimalFactory {
    Island island;
    public AnimalFactory(Island island) {
        this.island = island;
    }

    public static Eatable getNewAnimal(int x, int y, Eatable animal){
        for (Class<? extends Eatable> o : Settings.listOfAnimals) {
            if(animal.getClass() == o){
                try {
                    return o.getDeclaredConstructor(int.class, int.class).newInstance(x, y);
                } catch (Exception e ){
                    System.out.println("Ошибка при создании нового экземпляра животного!");
                }
            }
        }
        return null;
    }
}
