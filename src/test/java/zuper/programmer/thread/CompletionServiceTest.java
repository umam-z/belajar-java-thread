package zuper.programmer.thread;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.*;

public class CompletionServiceTest {
    private Random random = new Random();

    @Test
    void testSubmit() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        ExecutorCompletionService<String> executorCompletionService = new ExecutorCompletionService<>(service);

        // submit (data) task
        Executors.newSingleThreadExecutor().execute(
                new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 100; i++) {
                            final int index = i;
                            executorCompletionService.submit(
                                    new Callable<String>() {
                                        @Override
                                        public String call() throws Exception {
                                            Thread.sleep(random.nextInt(2000));
                                            return "Task-" + index;
                                        }
                                    }
                            );
                        }
                    }
                }
        );

        // poll data
        Executors.newSingleThreadExecutor().execute(
                new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                Future<String> poll = executorCompletionService.poll(5, TimeUnit.SECONDS);
                                if (poll == null) {
                                    break;
                                } else {
                                    System.out.println(poll.get());
                                }
                            } catch (InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                                break;
                            }

                        }
                    }
                }
        );
        service.awaitTermination(5, TimeUnit.MINUTES);
    }
}
