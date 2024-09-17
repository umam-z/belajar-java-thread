package zuper.programmer.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceTest {
    @Test
    void testExecutorService() throws InterruptedException {
        try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
            for (int i = 0; i < 100; i++) {
                executor.execute(
                        new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                    System.out.println("Runnable in thread: " + Thread.currentThread().getName());
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                );
            }
            executor.awaitTermination(5, TimeUnit.MINUTES);
        }
    }

    @Test
    void testExecutorServiceFix() throws InterruptedException {
        try (ExecutorService executor = Executors.newFixedThreadPool(10)) {
            for (int i = 0; i < 100; i++) {
                executor.execute(
                        new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                    System.out.println("Runnable in thread: " + Thread.currentThread().getName());
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                );
            }
            executor.shutdown();
            // executor.awaitTermination(5, TimeUnit.MINUTES);
        }
    }


}
