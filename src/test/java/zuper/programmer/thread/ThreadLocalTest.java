package zuper.programmer.thread;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadLocalTest {
    @Test
    void testCreate() throws InterruptedException {
        Random random = new Random();
        UserService userService = new UserService();
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 50; i++) {
            int index = i;
            executorService.execute(
                    new Runnable() {
                        @Override
                        public void run() {

                            try {
                                userService.setUser("User-" + index);
                                Thread.sleep(1000 + random.nextInt(2000));
                                userService.doAction();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );
        }
        executorService.awaitTermination(1, TimeUnit.MINUTES);
    }
}
