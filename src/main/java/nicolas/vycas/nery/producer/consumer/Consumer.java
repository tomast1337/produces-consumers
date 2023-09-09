package nicolas.vycas.nery.producer.consumer;

import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

public class Consumer implements Runnable {

    private final static Logger logger = Logger.getLogger(Consumer.class.getName());

    private final Semaphore empty;
    private final Semaphore full;
    private final int[] buffer;
    private final String name;

    public Consumer(Semaphore empty, Semaphore full, int[] buffer, String name) {
        this.empty = empty;
        this.full = full;
        this.buffer = buffer;
        this.name = name;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Wait until the buffer is not empty.
                full.acquire();

                // Consume all items in the that are not 0.
                for (int i = 0; i < buffer.length; i++) {
                    if (buffer[i] != 0) {
                        logger.info("Consumer " + name + " consumed: " + String.valueOf(buffer[i]));
                        buffer[i] = 0;
                    }
                }
                
                // Signal that the buffer is now empty.
                empty.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
