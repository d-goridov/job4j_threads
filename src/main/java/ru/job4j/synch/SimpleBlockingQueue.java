package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    private int limitSize;

    public SimpleBlockingQueue(int limitSize) {
        this.limitSize = limitSize;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() >= limitSize) {
            wait();
        }
        queue.add(value);
        notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.size() < 1) {
            wait();
        }
        T rsl = queue.poll();
        notifyAll();
        return rsl;
    }
}
