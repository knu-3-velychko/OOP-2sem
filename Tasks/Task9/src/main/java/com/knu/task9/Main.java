package com.knu.task9;

import com.knu.task9.queue.CasQueue;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Test");

        CasQueue<Integer> casQueue = new CasQueue<>();

        Thread small = new Thread(() -> {
            for (int i = 0; i < 25; i++) {
                casQueue.add(i);
            }
        });

        Thread big = new Thread(() -> {
            for (int i = 128; i < 150; i++)
                casQueue.add(i);
        });

        small.start();
        big.start();

        small.join();
        big.join();

        casQueue.print();
    }
}
