package util;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Randomizer {
    public static Random rand = new Random();

        public static int generateNum (int bound){
            int result = 0;
            result = ThreadLocalRandom.current().nextInt(bound);
        return result;
    }
}
