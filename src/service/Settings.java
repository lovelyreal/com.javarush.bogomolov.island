package service;

import Animals.Predators.*;
import Animals.Herbivore.*;
import Util.Eatable;
import Util.Plant;

import java.util.List;

public class Settings {
    public static Integer x = 100;
    public static Integer y = 20;
    public static Integer maxAmountOfAnimalsInOneCell = 2545;
    public static List<Class<? extends Eatable>> listOfAnimals = List.of(Wolf.class,Fox.class,Eagle.class,
            BoaConstrictor.class,Bear.class,Sheep.class,Rabbit.class,Mouse.class,Horse.class,
            Goat.class,Duck.class,Deer.class,Caterpillar.class,Buffalo.class,Boar.class,Plant.class);

}
