package ru.job4j.pool;

import ru.job4j.synch.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private int size = Runtime.getRuntime().availableProcessors();
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);


    public ThreadPool() {
         for (int i = 1; i <= size; i++) {
             Thread thread = new Thread(() -> {
                 while (!Thread.currentThread().isInterrupted()) {
                     try {
                         tasks.poll().run();
                     } catch (InterruptedException e) {
                         Thread.currentThread().interrupt();
                     }
                 }
             }, "User Thread №" + i);
             threads.add(thread);
         }
         threads.forEach(Thread::start);
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }
}
