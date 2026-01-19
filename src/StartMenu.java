
import service.AnimalLifeTask;
import service.Island;
import service.IslandInfoTask;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class StartMenu {
    public static void main(String[] args) throws InterruptedException {
        Island myIsland = new Island();
        myIsland.createNewIsland();
        System.out.println("/".repeat(100));
        try(ScheduledExecutorService executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
                ScheduledExecutorService infoExecutorService = Executors.newSingleThreadScheduledExecutor()){




            executorService.scheduleAtFixedRate(new AnimalLifeTask(myIsland), 1,2, TimeUnit.SECONDS);

            infoExecutorService.scheduleAtFixedRate(new IslandInfoTask(myIsland), 0,2, TimeUnit.SECONDS);

            Thread.sleep(100000);

        }

//        infoExecutorService.close();
//       ecutorService.close();


    }
}
