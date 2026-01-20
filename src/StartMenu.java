
import service.*;
import util.Settings;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class StartMenu {
    public static void main(String[] args) throws InterruptedException {
        Island myIsland = new Island();
        myIsland.createNewIsland();
        System.out.println("/".repeat(100));
        ScheduledThreadPoolExecutor executorService = new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors());
        ScheduledExecutorService infoExecutorService = Executors.newSingleThreadScheduledExecutor();

        for (int x = 0; x < Settings.MAP_SIZE_X; x++) {
            for (int y = 0; y < Settings.MAP_SIZE_Y; y++) {
                executorService.scheduleAtFixedRate(
                        new CellLifeTask(myIsland, x, y),
                        0, 2, TimeUnit.SECONDS
                );
            }
        }
        infoExecutorService.scheduleAtFixedRate(new IslandInfoTask(myIsland), 0, 2, TimeUnit.SECONDS);

        Thread.sleep(100000);

    }

//        infoExecutorService.close();
//       ecutorService.close();


}

