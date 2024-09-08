package zuper.programmer.thread;

import org.junit.jupiter.api.Test;

public class ThreadTest {
    @Test
    void testThread() {
        String name = Thread.currentThread().getName();
        System.out.println(name);
    }

    @Test
    void testCreateThread() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello from thread: " + Thread.currentThread().getName());
            }
        };
        runnable.run();

        Thread thread = new Thread(runnable);
        thread.start();

        System.out.println("program selesai");
    }

    @Test
    void testSleep() throws InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1_000L);
                    System.out.println("Hello from thread: " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        runnable.run();

        Thread thread = new Thread(runnable);
        thread.start();

        System.out.println("program selesai");
        Thread.sleep(2000L);
    }

    @Test
    void testJoin() throws InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1_000L);
                    System.out.println("Hello from thread: " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        runnable.run();

        Thread thread = new Thread(runnable);
        thread.start();
        System.out.println("Menunggu Selasai");
        thread.join();

        System.out.println("program selesai");
    }

//    @Test
//    void testInterrupt() throws InterruptedException {
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 10; i++) {
//                    System.out.println("Runnable: " + i);
//                    try {
//                        Thread.sleep(1_000L);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        };
//        // runnable.run();
//
//        Thread thread = new Thread(runnable);
//        thread.start();
//        Thread.sleep(5000);
//        thread.interrupt();
//        System.out.println("Menunggu Selasai");
//        thread.join();
//
//        System.out.println("program selesai");
//    }

    @Test
    void testInterruptCorrect() throws InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println("Runnable: " + i);
                    try {
                        Thread.sleep(1_000L);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        };
        // runnable.run();

        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(5000);
        thread.interrupt();
        System.out.println("Menunggu Selasai");
        thread.join();

        System.out.println("program selesai");
    }
}
