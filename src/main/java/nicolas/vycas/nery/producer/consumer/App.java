package nicolas.vycas.nery.producer.consumer;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App {
    private final static Logger logger = Logger.getLogger(App.class.getName());
    private static int BUFFER_SIZE;
    private static int NUM_PRODUCERS;
    private static int NUM_CONSUMERS;

    public static void main(String[] args) {

        logger.info("Producer/Consumer problem solution using semaphores.");

        BUFFER_SIZE = 10;
        NUM_PRODUCERS = 2;
        NUM_CONSUMERS = 2;

        try {
            BUFFER_SIZE = Integer.parseInt(args[0]);
        } catch (Exception e) {
            logger.info("Using the default for the buffer size.");
        }

        try {
            NUM_PRODUCERS = Integer.parseInt(args[0]);
        } catch (Exception e) {
            logger.info("Using the default for the number of producers.");
        }

        try {
            NUM_CONSUMERS = Integer.parseInt(args[0]);
        } catch (Exception e) {
            logger.info("Using the default for the number of consumers.");
        }

        // Create a buffer with 10 slots.
        int[] buffer = new int[BUFFER_SIZE];

        // Create a semaphore to track the number of empty slots in the buffer.
        Semaphore empty = new Semaphore(BUFFER_SIZE);
        // Create a semaphore to track the number of full slots in the buffer.
        Semaphore full = new Semaphore(0);

        Thread[] producers = new Thread[NUM_PRODUCERS];
        Thread[] consumers = new Thread[NUM_CONSUMERS];

        // Create and start the producer threads.
        for (int i = 0; i < NUM_PRODUCERS; i++) {
            producers[i] = new Thread(new Producer(empty, full, buffer, String.valueOf(i + 1)));
            producers[i].start();
        }

        // Create and start the consumer threads.
        for (int i = 0; i < NUM_CONSUMERS; i++) {
            consumers[i] = new Thread(new Consumer(empty, full, buffer, String.valueOf(i + 1)));
            consumers[i].start();
        }

        // Wait for the producer threads to finish.
        for (int i = 0; i < NUM_PRODUCERS; i++) {
            try {
                producers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Wait for the consumer threads to finish.
        for (int i = 0; i < NUM_CONSUMERS; i++) {
            try {
                consumers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
