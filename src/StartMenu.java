
import service.AnimalLifeTask;
import service.Island;
import service.IslandInfoTask;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class StartMenu {
    public static void main(String[] args) throws InterruptedException {
        long taskCount = 0;
        Island myIsland = new Island();
        myIsland.createNewIsland();
        System.out.println("/".repeat(100));
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors()-1);
        ScheduledExecutorService infoExecutorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new AnimalLifeTask(myIsland), 1,2, TimeUnit.SECONDS);

        infoExecutorService.scheduleAtFixedRate(new IslandInfoTask(myIsland), 0,2, TimeUnit.SECONDS);



//        infoExecutorService.close();
//        executorService.close();


    }
}
