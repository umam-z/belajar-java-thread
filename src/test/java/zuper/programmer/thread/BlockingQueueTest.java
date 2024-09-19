package zuper.programmer.thread;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.concurrent.*;

public class BlockingQueueTest {
    @Test
    void testArrayBlockingQueue() throws InterruptedException {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(5);
        ExecutorService service = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 10; i++) {
            service.execute(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                queue.put("Data");
                                System.out.println("Finish put data");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );
        }

        service.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                Thread.sleep(2000);
                                String take = queue.take();
                                System.out.println("receive data: " + take);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );
        service.awaitTermination(1, TimeUnit.MINUTES);
    }

    @Test
    void testPriorityBlockingQueue() throws InterruptedException {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>(10, Comparator.reverseOrder());
        ExecutorService service = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 10; i++) {
            int index = i;
            service.execute(
                    new Runnable() {
                        @Override
                        public void run() {
                            queue.put(index);
                            System.out.println("Finish put data");
                        }
                    }
            );
        }

        service.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                Thread.sleep(2000);
                                Integer take = queue.take();
                                System.out.println("receive data: " + take);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );
        service.awaitTermination(1, TimeUnit.MINUTES);
    }

    @Test
    void testLinkedBlockingQueue() throws InterruptedException {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        ExecutorService service = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 10; i++) {
            service.execute(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                queue.put("Data");
                                System.out.println("Finish put data");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );
        }

        service.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                Thread.sleep(2000);
                                String take = queue.take();
                                System.out.println("receive data: " + take);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );
        service.awaitTermination(1, TimeUnit.MINUTES);
    }

    @Test
    void testDelayQueue() throws InterruptedException {
        DelayQueue<ScheduledFuture<String>> queue = new DelayQueue<>();
        ExecutorService service = Executors.newFixedThreadPool(20);
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);

        for (int i = 1; i <= 10; i++) {
            int index = i;
            queue.put(executorService.schedule(
                    new Callable<String>() {
                        @Override
                        public String call() throws Exception {
                            return "Data: " + index;
                        }
                    },
                    i,
                    TimeUnit.SECONDS
            ));
        }

        service.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                ScheduledFuture<String> take = queue.take();
                                System.out.println("receive data: " + take.get());
                            } catch (InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );
        service.awaitTermination(1, TimeUnit.MINUTES);
    }

    @Test
    void testSynchronousQueue() throws InterruptedException {
        SynchronousQueue<String> queue = new SynchronousQueue<>();
        ExecutorService service = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 10; i++) {
            int index = i;
            service.execute(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                queue.put("Data: " + index);
                                System.out.println("Finish Put data: " + index);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.println("Finish put data");
                        }
                    }
            );
        }

        service.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                Thread.sleep(2000);
                                String take = queue.take();
                                System.out.println("receive data: " + take);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );
        service.awaitTermination(1, TimeUnit.MINUTES);
    }

    @Test
    void testBlockingDeque() throws InterruptedException {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        ExecutorService service = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 10; i++) {
            int index = i;
            try {
                deque.putLast("Data: " + index);
                System.out.println("Finish Put data: " + index);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        service.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                Thread.sleep(2000);
                                String take = deque.takeFirst();
                                System.out.println("receive data: " + take);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );
        service.awaitTermination(1, TimeUnit.MINUTES);
    }

    @Test
    void testTransferQueue() throws InterruptedException {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        ExecutorService service = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 10; i++) {
            int index = i;
            service.execute(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                queue.transfer("Data: " + index);
                                System.out.println("Finish Put data: " + index);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );
        }

        service.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                Thread.sleep(2000);
                                String take = queue.take();
                                System.out.println("receive data: " + take);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );
        service.awaitTermination(1, TimeUnit.MINUTES);
    }
}
