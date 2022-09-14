package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;

public class Wget implements Runnable {

    private final String url;
    private final int speed;
    private final String file;
    private static final int SEC = 1000;

    public Wget(String url, int speed, String file) {
        this.url = url;
        this.speed = speed;
        this.file = file;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream out = new FileOutputStream(file)) {
            int countByte;
            int downloadBytes = 0;
            byte[] dataBuffer = new byte[1024];
            long start = System.currentTimeMillis();
            while ((countByte = in.read(dataBuffer, 0, 1024)) != -1) {
                downloadBytes += countByte;
                if (downloadBytes >= speed) {
                    long end = System.currentTimeMillis();
                    long fact = end - start;
                    if (fact < SEC) {
                        try {
                            Thread.sleep(SEC - fact);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    start = System.currentTimeMillis();
                    downloadBytes = 0;
                }
                out.write(dataBuffer, 0, countByte);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void validate(String[] array) {
        if (array.length != 3) {
            throw new IllegalArgumentException("Count of args must be 3: "
                    + "url, speed, file");
        }
    }

    public static void main(String[] args) {
        validate(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String file = args[2];
        Thread wget = new Thread(new Wget(url, speed, file));
        wget.start();
        try {
            wget.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

