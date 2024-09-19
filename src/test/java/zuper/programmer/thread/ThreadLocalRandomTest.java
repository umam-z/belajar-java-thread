package zuper.programmer.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.IntConsumer;

public class ThreadLocalRandomTest {
    @Test
    void testCreate() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            executorService.execute(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                                int nextInt = ThreadLocalRandom.current().nextInt();
                                System.out.println(nextInt);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );
        }
        executorService.awaitTermination(1, TimeUnit.MINUTES);
    }

    @Test
    void testRandomStream() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        ThreadLocalRandom.
                                current().
                                ints(100, 0, 100).
                                forEach(new IntConsumer() {
                                    @Override
                                    public void accept(int value) {
                                        System.out.println(value);
                                    }
                                });
                    }
                }
        );
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
    }
}
