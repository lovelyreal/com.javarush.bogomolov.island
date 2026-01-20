
import service.*;
import util.Settings;

import java.util.concurrent.*;


public class StartMenu {
    public static void main(String[] args) throws InterruptedException {
        Island myIsland = new Island();
        myIsland.createNewIsland();
        System.out.println("/".repeat(100));
        ScheduledThreadPoolExecutor executorService = new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors());
        ScheduledExecutorService infoExecutorService = Executors.newSingleThreadScheduledExecutor();
        ExecutorService testExecutorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

        for (int tick = 0; tick < 100; tick++) {

            CountDownLatch latch = new CountDownLatch(Settings.MAP_SIZE_X * Settings.MAP_SIZE_Y);

            for (int x = 0; x < Settings.MAP_SIZE_X; x++) {
                for (int y = 0; y < Settings.MAP_SIZE_Y; y++) {
                    testExecutorService.submit(new CellLifeTask(myIsland, x, y, latch));
                }
            }

            latch.await();
            new IslandInfoTask(myIsland,tick).run();

            Thread.sleep(500);
        }


    }

//        infoExecutorService.close();
//       ecutorService.close();


}

