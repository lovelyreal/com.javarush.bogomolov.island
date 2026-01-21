package service;

import java.util.concurrent.*;

public class SimulationWorker {
    Island myIsland = Island.getInstance();
    static final ScheduledExecutorService workers = Executors.newScheduledThreadPool(4);
    final ScheduledExecutorService info = Executors.newSingleThreadScheduledExecutor();
    public void start() {
        tick();
    }


    private void tick() {
        info.scheduleAtFixedRate(new IslandInfoTask(myIsland, 0) , 0, 2, TimeUnit.SECONDS);


        Island.Location[][] locations = myIsland.getLocations();

        for (Island.Location[] row : locations) {
            for (Island.Location loc : row) {
                workers.scheduleAtFixedRate(new AnimalLifeTask(loc, myIsland) , 1, 2, TimeUnit.SECONDS);
            }
        }
    }
}
