package com.knu.task7;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {

        ExecutorService executeService = Executors.newFixedThreadPool(10);
        ReaderWriterPattern lock = new ReaderWriterPattern();

        for (int i = 0; i < 5; i++) {
            long writingTime = ThreadLocalRandom.current().nextLong(5000);
            executeService.submit(new MyWriter("Writer " + i, lock, writingTime));
        }
        System.out.println( "Writers added...");

        for (int i = 0; i < 5; i++) {
            long readingTime = ThreadLocalRandom.current().nextLong(10);
            executeService.submit(new MyReader("Reader " + i, lock, readingTime));
        }
        System.out.println( "Readers added...");

        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            System.out.println( "Error sleeping before adding more readers");
            Thread.currentThread().interrupt();
        }

        for (int i = 6; i < 10; i++) {
            long readingTime = ThreadLocalRandom.current().nextLong(10);
            executeService.submit(new MyReader("Reader " + i, lock, readingTime));
        }
        System.out.println( "More readers added...");

        executeService.shutdown();
        try {
            executeService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println( "Error waiting for ExecutorService shutdown");
            Thread.currentThread().interrupt();
        }

    }
}