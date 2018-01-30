package ru.javawebinar.basejava;

public class MainDeadLock {
    private static final Object keyA = new Object();
    private static final Object keyB = new Object();

    public static void main(String[] args) {
        thread0.start();
        thread1.start();
    }

    private static Thread thread0 = new Thread(() -> {
        synchronized (keyA) {
            System.out.println(Thread.currentThread() + " got key A");
            try {
                Thread.sleep(500);
                System.out.println(Thread.currentThread() + " trying to get key B");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (keyB) {
                System.out.println(Thread.currentThread() + " got key B");
            }
        }
    });

    private static Thread thread1 = new Thread(() -> {
        synchronized (keyB) {
            System.out.println(Thread.currentThread() + " got key B");
            try {
                Thread.sleep(500);
                System.out.println(Thread.currentThread() + " trying to get key A");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (keyA) {
                System.out.println(Thread.currentThread() + " got key A");
            }
        }
    });
}