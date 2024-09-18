package zuper.programmer.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountDownLatchTest {
    @Test
    void testCreate() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        ExecutorService service = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 5; i++) {
            service.execute(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                System.out.println("Start Task");
                                Thread.sleep(2000);
                                System.out.println("Finish Task");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } finally {
                                countDownLatch.countDown();
                            }
                        }
                    }
            );
        }

        service.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            countDownLatch.await();
                            System.out.println("Finish All Task");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        service.awaitTermination(1, TimeUnit.MINUTES);
    }
}
