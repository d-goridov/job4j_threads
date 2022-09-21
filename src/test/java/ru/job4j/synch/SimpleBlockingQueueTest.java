package ru.job4j.synch;

import org.junit.Test;

public class SimpleBlockingQueueTest {

    @Test
    public void isWorking() throws InterruptedException {
        SimpleBlockingQueue<Integer> rsl = new SimpleBlockingQueue<>(10);
        Thread thread = new Thread(() -> {
            try {
                rsl.offer(5);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        Thread thread1 = new Thread(() -> {
            try {
                rsl.offer(5);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        thread.start();
        thread1.start();

        thread.join();
        thread.join();
    }
}