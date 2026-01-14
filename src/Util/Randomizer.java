package Util;
import java.util.Random;

public class Randomizer {
    public static Random rand = new Random();

        public static int generateNum ( int bound){
            int result = 0;
        try{
            result = rand.nextInt(bound);
        } catch (RuntimeException e) {
            System.out.println(bound);
        }
        return result;
    }
}
