package zuper.programmer.thread;

import org.junit.jupiter.api.Test;

public class ThreadCommunicationTest {
    private String message = null;

    @Test
    void testManual() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (message == null){

                }
                System.out.println(message);
            }
        });

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                message = "Message";
            }
        });

        thread.start();
        thread1.start();

        thread.join();
        thread1.join();
    }

    @Test
    void testWaitNotifyAll() throws InterruptedException {
        final var lock = new Object();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        lock.wait();
                        System.out.println(message);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        lock.wait();
                        System.out.println(message);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock){
                    message = "Message";
                    lock.notifyAll();
                }
            }
        });

        thread.start();
        thread1.start();
        thread2.start();

        thread.join();
        thread1.join();
        thread2.join();
    }

    @Test
    void testWaitNotify() throws InterruptedException {
        final var lock = new Object();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        lock.wait();
                        System.out.println(message);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock){
                    message = "Message";
                    lock.notify();
                }
            }
        });

        thread.start();
        thread1.start();

        thread.join();
        thread1.join();
    }
}
