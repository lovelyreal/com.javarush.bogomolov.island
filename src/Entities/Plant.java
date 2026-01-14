package Entities;

import Util.Eatable;

public class Plant implements Eatable {
    private int x;
    private int y;
    public static int maxAmountInOneCell = 200;

    public Plant(int x, int y) {
        this.x = x;
        this.y = y;

    }

    @Override
    public String toString() {
        return "\uD83C\uDF3F";
    }
}
