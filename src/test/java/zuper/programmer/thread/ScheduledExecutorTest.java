package zuper.programmer.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class ScheduledExecutorTest {
    @Test
    void testDelayedJob() throws InterruptedException {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
        ScheduledFuture<?> future = service.schedule(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Hello Scheduled");
                    }
                },
                5,
                TimeUnit.SECONDS
        );
        System.out.println(future.getDelay(TimeUnit.MILLISECONDS));
        service.awaitTermination(1, TimeUnit.MINUTES);
    }

    @Test
    void testPeriodicJob() throws InterruptedException {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
        ScheduledFuture<?> future = service.scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Hello Scheduled");
                    }
                },
                2,
                2,
                TimeUnit.SECONDS
        );
        System.out.println(future.getDelay(TimeUnit.MILLISECONDS));
        service.awaitTermination(1, TimeUnit.MINUTES);
    }
}
