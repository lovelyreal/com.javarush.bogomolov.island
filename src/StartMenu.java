import Util.Settings;
import service.Island;
import service.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StartMenu {
    public static void main(String[] args) {
        Island myIsland = new Island();
        myIsland.createNewIsland();

        System.out.println("/".repeat(100));
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(8);
        for (int i = 0; i < Settings.x; i++) {
            for (int j = 0; j < Settings.y; j++) {
                executorService.scheduleAtFixedRate(new Task(i,j,myIsland), 1, 2, TimeUnit.SECONDS);

            }
        }


    }
}
