package nicolas.vycas.nery.producer.consumer;

import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

public class Consumer implements Runnable {

    private final static Logger logger = Logger.getLogger(Consumer.class.getName());

    private final int[] buffer;
    private final Semaphore empty;
    private final Semaphore full;
    private final Semaphore mutex;
    private final String name;

    public Consumer(Semaphore empty, Semaphore full, Semaphore mutex, int[] buffer, String name) {
        this.empty = empty;
        this.full = full;
        this.buffer = buffer;
        this.name = name;
        this.mutex = mutex;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Acquire the empty semaphore.
                empty.acquire();

                // Acquire the mutex semaphore.
                mutex.acquire();

                // Consume all items in the that are not 0.
                for (int i = 0; i < buffer.length; i++) {
                    if (buffer[i] != 0) {
                        logger.info("Consumer " + name + " consumed: " + String.valueOf(buffer[i]));
                        buffer[i] = 0;
                    }
                }

                // Release the mutex semaphore.
                mutex.release();

                // Release the full semaphore.
                full.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
