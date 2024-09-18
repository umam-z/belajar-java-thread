package zuper.programmer.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExchangerTest {
    @Test
    void testCreate() throws InterruptedException {
        Exchanger<String> stringExchanger = new Exchanger<>();
        ExecutorService service = Executors.newFixedThreadPool(10);

        service.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            System.out.println("Thread 1 : Send : Hello");
                            Thread.sleep(1000);
                            String hello = stringExchanger.exchange("Hello");
                            System.out.println("Thread 1 : Receive : " + hello);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        service.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            System.out.println("Thread 2 : Send : World");
                            Thread.sleep(2000);
                            String world = stringExchanger.exchange("World");
                            System.out.println("Thread 2 : Receive : " + world);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        service.awaitTermination(1, TimeUnit.MINUTES);
    }
}
