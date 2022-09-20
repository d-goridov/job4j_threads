package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    private Queue<T> queue = new LinkedList<>();

    private int limitSize;

    @GuardedBy("this")
    private int count = 0;

    public SimpleBlockingQueue(int limitSize) {
        this.limitSize = limitSize;
    }

    public synchronized void offer(T value) {
        while (count >= limitSize) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        queue.add(value);
        count++;
        notifyAll();
    }

    public synchronized T poll() {
        while (count < 1) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        T rsl = queue.poll();
        count--;
        notifyAll();
        return rsl;
    }
}
