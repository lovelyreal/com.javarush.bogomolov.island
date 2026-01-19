package util;

import entity.Eatable;
import entity.animal.herbivore.*;
import entity.animal.predator.*;
import entity.Plant;

import java.util.List;

public class Settings {
    public static Integer MAP_SIZE_X = 100;
    public static Integer MAP_SIZE_Y = 20;
    public static List<Class<? extends Eatable>> listOfAnimals = List.of(Wolf.class,Fox.class,Eagle.class,
            BoaConstrictor.class,Bear.class,Sheep.class,Rabbit.class,Mouse.class,Horse.class,
            Goat.class,Duck.class,Deer.class,Caterpillar.class,Buffalo.class,Boar.class,Plant.class);
}
