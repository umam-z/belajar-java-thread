package zuper.programmer.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreTest {
    @Test
    void testSemaphore() throws InterruptedException {
        Semaphore semaphore = new Semaphore(10);
        ExecutorService service = Executors.newFixedThreadPool(100);

        for (int i = 0; i < 100; i++) {
            service.execute(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                semaphore.acquire();
                                Thread.sleep(1000L);
                                System.out.println("Selesai");
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            } finally {
                                semaphore.release();
                            }
                        }
                    }
            );
        }

        service.awaitTermination(1, TimeUnit.MINUTES);
    }
}
