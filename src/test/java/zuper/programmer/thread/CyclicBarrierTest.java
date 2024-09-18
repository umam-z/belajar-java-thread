package zuper.programmer.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class CyclicBarrierTest {
    @Test
    void testCreate() throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        ExecutorService service = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 5; i++) {
            service.execute(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                System.out.println("Waiting");
                                cyclicBarrier.await();
                                System.out.println("Done Waiting");
                            } catch (InterruptedException | BrokenBarrierException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );
        }
        service.awaitTermination(1, TimeUnit.MINUTES);
    }
}
