package zuper.programmer.thread;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.*;
import java.util.function.Function;

public class CompletableFutureTest {
    private ExecutorService service = Executors.newFixedThreadPool(10);
    Random random = new Random();

    public CompletableFuture<String> getValue() {
        CompletableFuture<String> future = new CompletableFuture<>();
        service.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            future.complete("Mohammad Sirajul Umam");
                        } catch (InterruptedException e) {
                            future.completeExceptionally(e);
                        }
                    }
                }
        );
        return future;
    }

    @Test
    void testCreate() throws ExecutionException, InterruptedException {
        Future<String> value = getValue();
        System.out.println(value.get());
    }

    private void execute(CompletableFuture<String> future, String value) {
        service.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(100 + random.nextInt(5000));
                            future.complete(value);
                        } catch (InterruptedException e) {
                            future.completeExceptionally(e);
                        }
                    }
                }
        );
    }

    public Future<String> getFastest(){
        CompletableFuture<String> future = new CompletableFuture<>();
        execute(future, "Thread 1");
        execute(future, "Thread 2");
        execute(future, "Thread 3");
        return future;
    }

    @Test
    void testFastest() throws ExecutionException, InterruptedException {
        System.out.println(getFastest().get());
    }

    @Test
    void testCompletionStage() throws ExecutionException, InterruptedException {
        CompletableFuture<String> value = getValue();
        CompletableFuture<String[]> value2 = value.thenApply(
                new Function<String, String>() {
                    @Override
                    public String apply(String s) {
                        return s.toUpperCase();
                    }
                }
        ).thenApply(
                new Function<String, String[]>() {
                    @Override
                    public String[] apply(String s1) {
                        return s1.split(" ");
                    }
                }
        );
        String[] names = value2.get();

        for (String name : names) {
            System.out.println(name);
        }
    }
}
