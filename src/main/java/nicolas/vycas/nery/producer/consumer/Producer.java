package nicolas.vycas.nery.producer.consumer;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

public class Producer implements Runnable {

    private final static Logger logger = Logger.getLogger(Producer.class.getName());

    private final int[] buffer;
    private final Semaphore empty;
    private final Semaphore full;
    private final Semaphore mutex;
    private final String name;

    public Producer(Semaphore empty, Semaphore full, Semaphore mutex, int[] buffer, String name) {
        this.empty = empty;
        this.full = full;
        this.buffer = buffer;
        this.name = name;
        this.mutex = mutex;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            try {
                // Acquire the full semaphore.
                full.acquire();

                // Acquire the mutex semaphore.
                mutex.acquire();

                // Generate random numbers and add it to the buffer.
                for (int i = 0; i < buffer.length; i++) {
                    if (buffer[i] == 0) {
                        buffer[i] = random.nextInt(100) + 1;
                        logger.info("Producer " + name + " produced: " + String.valueOf(buffer[i]));
                        break;
                    }
                }

                // Release the mutex semaphore.
                mutex.release();

                // Release the empty semaphore.
                empty.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
