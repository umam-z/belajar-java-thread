package zuper.programmer.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class PhaserTest {
    @Test
    void testPhaserAsCountDownLatch() throws InterruptedException {
        Phaser phaser = new Phaser();
        ExecutorService service = Executors.newFixedThreadPool(10);

        phaser.bulkRegister(5);
        for (int i = 0; i < 5; i++) {
            service.execute(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                System.out.println("Start Task");
                                Thread.sleep(1000);
                                System.out.println("Finish Task");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } finally {
                                phaser.arrive();
                            }
                        }
                    }
            );
        }

        service.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        phaser.awaitAdvance(0);
                        System.out.println("All Task Done");
                    }
                }
        );

        service.awaitTermination(1, TimeUnit.MINUTES);
    }

    @Test
    void testPhaserAsCyclicBarrier() throws InterruptedException {
        Phaser phaser = new Phaser();
        ExecutorService service = Executors.newFixedThreadPool(10);

        phaser.bulkRegister(5);
        for (int i = 0; i < 5; i++) {
            service.execute(
                    new Runnable() {
                        @Override
                        public void run() {
                            phaser.arriveAndAwaitAdvance();
                            System.out.println("Done");
                        }
                    }
            );
        }

        service.awaitTermination(1, TimeUnit.MINUTES);
    }
}
