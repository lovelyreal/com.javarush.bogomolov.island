package Util;

import java.util.Map;

public abstract class Animal implements Eatable{
    protected static Integer x;
    protected static Integer y;
    protected double weight;
    protected Integer maxCellsByMove;
    protected double killosOfMealToSatisfaction;
    protected Map<Class<? extends Eatable>,Integer> diet;

}
