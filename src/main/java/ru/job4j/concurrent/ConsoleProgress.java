package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        String[] array = {"/", "|", "\\", "—"};
        while (!Thread.currentThread().isInterrupted()) {
            try {
                for (String s: array) {
                    System.out.print("\rLoading..." + s);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread myThread = new Thread(new ConsoleProgress());
        myThread.start();
        Thread.sleep(1000);
        myThread.interrupt();
    }
}
