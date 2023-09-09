package nicolas.vycas.nery.producer.consumer;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

public class Producer implements Runnable {

    private final static Logger logger = Logger.getLogger(Producer.class.getName());

    private final Semaphore empty;
    private final Semaphore full;
    private final int[] buffer; // The buffer is a reference to the same array in the main class.
    private final String name;
    private final Random random = new Random();

    public Producer(Semaphore empty, Semaphore full, int[] buffer, String name) {
        this.empty = empty;
        this.full = full;
        this.buffer = buffer;
        this.name = name;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Wait until the buffer is not full.
                empty.acquire();
                // Generate random numbers and add it to the buffer.
                for (int i = 0; i < buffer.length; i++) {
                    if (buffer[i] == 0) {
                        buffer[i] = random.nextInt(100) + 1;
                        logger.info("Producer " + name + " produced: " + String.valueOf(buffer[i]));
                        break;
                    }
                }
                // Signal that the buffer is now full.
                full.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
