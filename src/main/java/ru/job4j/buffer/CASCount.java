package ru.job4j.buffer;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;


@ThreadSafe
public class CASCount {

    private final AtomicReference<Integer> count = new AtomicReference<>();

    public CASCount(int startValue) {
        count.set(startValue);
    }

   public void increment() {
        Integer value;
        do {
            value = count.get();
        } while (!count.compareAndSet(value, value + 1));
   }

    public int get() {
        return count.get();
    }
}
