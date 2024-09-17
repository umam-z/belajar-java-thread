package zuper.programmer.thread;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FutureTest {
    @Test
    void testFuture() throws ExecutionException, InterruptedException {
        try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
            Future<String> future = executor.submit(
                    new Callable<String>() {
                        @Override
                        public String call() throws Exception {
                            Thread.sleep(5000);
                            return "Hi";
                        }
                    }
            );
            System.out.println("Selesai future");
            while (!future.isDone()) {
                System.out.println("Waiting future");
                Thread.sleep(1000);
            }
            System.out.println(future.get());
        }
    }

    @Test
    void testFutureCencel() throws ExecutionException, InterruptedException {
        try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
            Future<String> future = executor.submit(
                    new Callable<String>() {
                        @Override
                        public String call() throws Exception {
                            Thread.sleep(5000);
                            return "Hi";
                        }
                    }
            );
            System.out.println("Selesai future");
            Thread.sleep(2000);
            future.cancel(true);
            System.out.println(future.isCancelled());
            System.out.println(future.get());
        }
    }

    @Test
    void testInvokeAll() throws InterruptedException, ExecutionException {
        try (ExecutorService service = Executors.newFixedThreadPool(10)) {

            List<Callable<String>> callables = IntStream.range(1, 11).mapToObj(
                    new IntFunction<Callable<String>>() {
                        @Override
                        public Callable<String> apply(int value) {
                            return new Callable<String>() {
                                @Override
                                public String call() throws Exception {
                                    Thread.sleep(value * 500L);
                                    return String.valueOf(value);
                                }
                            };
                        }
                    }
            ).collect(Collectors.toList());

            List<Future<String>> futures = service.invokeAll(callables);

            for(Future<String> stringFuture : futures)
            {
                System.out.println(stringFuture.get());
            }
        }

    }

    @Test
    void testInvokeAny() throws InterruptedException, ExecutionException {
        try (ExecutorService service = Executors.newFixedThreadPool(10)) {

            List<Callable<String>> callables = IntStream.range(1, 11).mapToObj(
                    new IntFunction<Callable<String>>() {
                        @Override
                        public Callable<String> apply(int value) {
                            return new Callable<String>() {
                                @Override
                                public String call() throws Exception {
                                    Thread.sleep(value * 500L);
                                    return String.valueOf(value);
                                }
                            };
                        }
                    }
            ).collect(Collectors.toList());

            String any = service.invokeAny(callables);
            System.out.println(any);
        }

    }
}
