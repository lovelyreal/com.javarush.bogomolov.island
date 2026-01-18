package util;

import entity.Eatable;
import service.*;

import java.lang.reflect.InvocationTargetException;

public class AnimalFactory {

    public static Eatable createNewAnimal(int x, int y, Class<? extends Eatable> animal){
        try{
            return animal.getDeclaredConstructor(int.class,int.class).newInstance(x,y);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}

