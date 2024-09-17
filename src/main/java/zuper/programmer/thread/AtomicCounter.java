package zuper.programmer.thread;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicCounter {
    private AtomicLong atomicLong = new AtomicLong(0L);

    public void increment() {
        atomicLong.incrementAndGet();
    }

    public Long getValue() {
        return atomicLong.get();
    }
}
