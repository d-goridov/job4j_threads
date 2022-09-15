package ru.job4j.jcip;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class Counter {

    private final Object lock = new Object();

    @GuardedBy("lock")
    private int value;

    public void increment() {
        synchronized (this.lock) {
            this.value++;
        }
    }

    public int get() {
        synchronized (this.lock) {
            return this.value;
        }
    }
}
