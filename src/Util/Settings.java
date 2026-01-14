package Util;

import Entities.Animals.Herbivore.*;
import Entities.Animals.Predators.*;
import Entities.Plant;

import java.util.List;

public class Settings {
    public static Integer x = 2;
    public static Integer y = 3;
    public static Integer maxAmountOfAnimalsInOneCell = 2545;
    public static List<Class<? extends Eatable>> listOfAnimals = List.of(Wolf.class,Fox.class,Eagle.class,
            BoaConstrictor.class,Bear.class,Sheep.class,Rabbit.class,Mouse.class,Horse.class,
            Goat.class,Duck.class,Deer.class,Caterpillar.class,Buffalo.class,Boar.class,Plant.class);



}
