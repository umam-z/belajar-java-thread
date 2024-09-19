package zuper.programmer.thread;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.BiConsumer;

public class ConcurrentMapTest {
    @Test
    void testCreate() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(100);
        ConcurrentHashMap<Integer, String> hashMap = new ConcurrentHashMap<>();
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        for (int i = 0; i < 100; i++) {
            int index = i;
            executorService.execute(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                                hashMap.putIfAbsent(index, "Data ke-" + index);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } finally {
                                countDownLatch.countDown();
                            }
                        }
                    }
            );
        }

        executorService.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            countDownLatch.await();
                            hashMap.forEach(
                                    new BiConsumer<Integer, String>() {
                                        @Override
                                        public void accept(Integer integer, String s) {
                                            System.out.println(integer + " : " + s);
                                        }
                                    }
                            );
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        );

        executorService.awaitTermination(1, TimeUnit.MINUTES);
    }

    @Test
    void testCollection() {
        List<String> names = List.of("Mohammad", "Sirajul", "Umam");
        List<String> synchronizedList = Collections.synchronizedList(names);
    }
}
