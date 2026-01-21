package service;

import java.util.concurrent.CountDownLatch;

public class CellLifeTask implements Runnable {

    private final Island.Location location;
    private final Island.Location[][] locations;
    private final int x;
    private final int y;
    private final CountDownLatch latch;

    public CellLifeTask(Island island, int x, int y, CountDownLatch latch) {
        this.locations = island.getLocations();
        this.location = locations[x][y];
        this.x = x;
        this.y = y;
        this.latch = latch;
    }

    @Override
    public void run() {
        location.reentrantLock.lock();
        try {
            busy();
            //location.move(x, y, locations);
            //location.eatingProcess(x, y);
            //location.breed();
        } finally {
            location.reentrantLock.unlock();
            latch.countDown();
        }
    }
    private void busy(){
        for (int i = 0; i < 5000; i++) {
            Math.sqrt(i);
        }
    }
}