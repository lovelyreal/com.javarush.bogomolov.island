
import service.AnimalBreedTask;
import service.AnimalMoveTask;
import service.Island;
import service.IslandInfoTask;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class StartMenu {
    public static void main(String[] args) throws InterruptedException {
        Island myIsland = new Island();
        myIsland.createNewIsland();
        System.out.println("/".repeat(100));
        try(ScheduledThreadPoolExecutor executorService = new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors());
            ScheduledExecutorService infoExecutorService = Executors.newSingleThreadScheduledExecutor()){




            executorService.scheduleAtFixedRate(new AnimalMoveTask(myIsland), 0,2, TimeUnit.SECONDS);
            executorService.scheduleAtFixedRate(new AnimalBreedTask(myIsland), 0,2, TimeUnit.SECONDS);

            infoExecutorService.scheduleAtFixedRate(new IslandInfoTask(myIsland), 0,2, TimeUnit.SECONDS);

            Thread.sleep(100000);

        }

//        infoExecutorService.close();
//       ecutorService.close();


    }
}
