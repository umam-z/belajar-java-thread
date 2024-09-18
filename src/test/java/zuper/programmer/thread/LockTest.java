package zuper.programmer.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {
    @Test
    void testLock() throws InterruptedException {
        CounterLock counter = new CounterLock();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    counter.increment();
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

        System.out.println(counter.getValue());
    }

    @Test
    void testReadWriteLock() throws InterruptedException {
        CounterReadWriteLock counter = new CounterReadWriteLock();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    counter.increment();
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

        System.out.println(counter.getValue());
    }

    String message;

    @Test
    void testCondition() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();
        Thread thread = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            reentrantLock.lock();
                            condition.await();
                            System.out.println(message);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            reentrantLock.unlock();
                        }
                    }
                }
        );

        Thread thread1 = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            reentrantLock.lock();
                            Thread.sleep(2000L);
                            message = "Hello";
                            // untuk mengirimkan sinyal kesemua thread menggunakan signalAll
                            // condition.signalAll();
                            condition.signal();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            reentrantLock.unlock();
                        }
                    }
                }
        );
        thread.start();
        thread1.start();

        thread.join();
        thread1.join();
    }
}
