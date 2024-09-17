package zuper.programmer.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {
    @Test
    void testCreate() throws InterruptedException {
        Integer minThread = 10;
        Integer maxThread = 100;
        Integer alive = 1;
        TimeUnit minutes = TimeUnit.MINUTES;
        ArrayBlockingQueue<Runnable> runnables = new ArrayBlockingQueue<Runnable>(100);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(minThread, maxThread, alive, minutes, runnables);
    }

    @Test
    void testExecuteRunnable() throws InterruptedException {
        Integer minThread = 10;
        Integer maxThread = 100;
        Integer alive = 1;
        TimeUnit minutes = TimeUnit.MINUTES;
        ArrayBlockingQueue<Runnable> runnables = new ArrayBlockingQueue<Runnable>(100);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(minThread, maxThread, alive, minutes, runnables);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    System.out.println("Runnable from thread: " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread.sleep(6000);
    }

    @Test
    void testShutdown() throws InterruptedException {
        Integer minThread = 10;
        Integer maxThread = 100;
        Integer alive = 1;
        TimeUnit minutes = TimeUnit.MINUTES;
        ArrayBlockingQueue<Runnable> runnables = new ArrayBlockingQueue<Runnable>(1000);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(minThread, maxThread, alive, minutes, runnables);

        for (int i = 0; i < 1000; i++) {
            final var task = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        System.out.println("Task "+ task +" from thread: " + Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

        // executor.shutdown();
        // executor.awaitTermination(1, TimeUnit.DAYS);
        executor.shutdownNow();
    }

    @Test
    void testRejected() throws InterruptedException {
        Integer minThread = 10;
        Integer maxThread = 100;
        Integer alive = 1;
        TimeUnit minutes = TimeUnit.MINUTES;
        var handler = new LogRejectedExecutionHandler();
        ArrayBlockingQueue<Runnable> runnables = new ArrayBlockingQueue<Runnable>(10);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                minThread,
                maxThread,
                alive,
                minutes,
                runnables,
                handler);

        for (int i = 0; i < 1000; i++) {
            final var task = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        System.out.println("Task "+ task +" from thread: " + Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

        // executor.shutdown();
         executor.awaitTermination(1, TimeUnit.DAYS);
        // executor.shutdownNow();
    }

    public static class LogRejectedExecutionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("Task: " + r + " is rejected");
        }
    }
}