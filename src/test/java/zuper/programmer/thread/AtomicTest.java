package zuper.programmer.thread;

import org.junit.jupiter.api.Test;

public class AtomicTest {
    @Test
    void testAtomicCounter() throws InterruptedException {
        AtomicCounter atomicCounter = new AtomicCounter();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    atomicCounter.increment();
                }
            }
        };
        Thread thread = new Thread(runnable);
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);

        thread.start();
        thread1.start();
        thread2.start();

        thread.join();
        thread1.join();
        thread2.join();

        System.out.println(atomicCounter.getValue());
    }
}
