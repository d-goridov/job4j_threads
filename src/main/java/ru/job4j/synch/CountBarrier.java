package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class CountBarrier {

    private final Object monitor = this;

    private final int total;

    @GuardedBy("monitor")
    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public synchronized void count() {
        synchronized (monitor) {
            count++;
            notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            while (count != total) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
