package zuper.programmer.thread;

import org.junit.jupiter.api.Test;

public class DeadlockTest {
    @Test
    void testTransfer() throws InterruptedException {
        Balance balance = new Balance(1_000_000L);
        Balance balance1 = new Balance(1_000_000L);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Balance.transfer(balance, balance1, 500000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Balance.transfer(balance1, balance, 500000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        thread.start();
        thread1.start();

        thread1.join();
        thread.join();

        System.out.println(balance.getValue());
        System.out.println(balance1.getValue());
    }
}
