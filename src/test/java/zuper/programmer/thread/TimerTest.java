package zuper.programmer.thread;

import org.junit.jupiter.api.Test;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {
    @Test
    void testDelayedJob() throws InterruptedException {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Delayed Job");
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, 2000L);

        Thread.sleep(3000L);
    }

    @Test
    void testPeriodicJob() throws InterruptedException {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Delayed Job");
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, 2000L, 2000L);

        Thread.sleep(10000L);
    }
}
