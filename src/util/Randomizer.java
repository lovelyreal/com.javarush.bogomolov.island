package util;
import java.util.Random;

public class Randomizer {
    public static Random rand = new Random();

        public static int generateNum (int bound){
            int result = 0;
            result = rand.nextInt(bound);
        return result;
    }
}
