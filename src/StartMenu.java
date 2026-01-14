import Util.Settings;
import service.Island;
import service.Task;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class StartMenu {
    public static void main(String[] args) throws InterruptedException {
        long taskCount = 0;
        Island myIsland = new Island();
        myIsland.createNewIsland();
        myIsland.info();
        System.out.println("/".repeat(100));
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);
        //System.out.println("ExecutorService создан: " + executorService);
        for (int i = 0; i < Settings.x; i++) {
            for (int j = 0; j < Settings.y; j++) {
                //System.out.println("Создаю задачу для [" + i + "][" + j + "]");
                Task task = new Task(i, j, myIsland);
                Thread nThread = new Thread(task);
                nThread.start();
                nThread.join();
            }

        }
        myIsland.info();


    }
}
