
import service.*;
import util.Settings;

import java.util.concurrent.*;


public class StartMenu {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("/".repeat(100));
        Island.initialize();
        final SimulationWorker simulationWorker = new SimulationWorker();
        simulationWorker.start();


    }


}

