package service;

import java.util.concurrent.*;

public class SimulationWorker {
    Island myIsland = Island.getInstance();
    static final ExecutorService workers = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    final ScheduledExecutorService ticker = Executors.newSingleThreadScheduledExecutor();
    public void start() {
        ticker.scheduleAtFixedRate(
                this::tick,
                0,
                2,
                TimeUnit.SECONDS
        );
    }
    private volatile boolean running = false;

    private void tick() {
        if (running) return;
        running = true;

        Island.Location[][] locations = myIsland.getLocations();
        int total = locations.length * locations[0].length;
        CountDownLatch latch = new CountDownLatch(total);

        for (Island.Location[] row : locations) {
            for (Island.Location loc : row) {
                workers.submit(() -> {
                    try {
                        new AnimalLifeTask(loc, myIsland).run();
                    } finally {
                        latch.countDown();
                    }
                });
            }
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            running = false;
        }
    }
}
