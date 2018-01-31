package ru.javawebinar.basejava;

public class MainDeadLock {
    private static final String keyA = "key A";
    private static final String keyB = "key B";

    public static void main(String[] args) {
        thread0.start();
        thread1.start();
    }

    private static Thread thread0 = new Thread(() -> lock(keyA, keyB));

    private static Thread thread1 = new Thread(() -> lock(keyB, keyA));

    private static void lock(String a, String b) {
        synchronized (a) {
            System.out.println(Thread.currentThread() + " got " + a);
            try {
                Thread.sleep(500);
                System.out.println(Thread.currentThread() + " trying to get " + b);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (b) {
                System.out.println(Thread.currentThread() + " got " + b);
            }
        }
    }
}