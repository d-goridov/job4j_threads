package ru.job4j.buffer;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class CASCountTest {

    @Test
    public void whenIncrement() throws InterruptedException {
        CASCount ref = new CASCount(200);
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                ref.increment();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                ref.increment();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        assertThat(ref.get(), is(400));
    }
}