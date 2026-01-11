package service;

import Animals.Predators.*;
import Animals.Herbivore.*;
import Util.Animal;

import java.util.ArrayList;
import java.util.List;

public class Settings {
    public static Integer x = 100;
    public static Integer y = 20;
    public static Integer maxEmountOfAnimalsInOneCell = 2545;
//    public static List<Class<? extends Animal>> listOfAnimals = List.of(Wolf.class,Fox.class,Eagle.class,
//            BoaConstrictor.class,Bear.class,Sheep.class,Rabbit.class,Mouse.class,Horse.class,
//            Goat.class,Duck.class,Deer.class,Caterpillar.class,Buffalo.class,Boar.class);
    public static List<Class<? extends Animal>> listOfAnimals = List.of(Wolf.class, Fox.class);
}
