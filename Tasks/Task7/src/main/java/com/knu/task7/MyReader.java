package com.knu.task7;

public class MyReader implements Runnable {

    private ReaderWriterPattern safeLock;
    private String name;
    private long readingTime;

    public MyReader(String name, ReaderWriterPattern lock, long readingTime) {
        this.name = name;
        this.safeLock = lock;
        this.readingTime = readingTime;
    }

    @Override
    public void run() {
        safeLock.lockReader();
        try {
            read();
        } catch (InterruptedException e) {
            System.out.println( "InterruptedException when reading");
            Thread.currentThread().interrupt();
        } finally {
            safeLock.unlockReader();
        }
    }

    public void read() throws InterruptedException {
        System.out.println( String.format("%s begin", name));
        Thread.sleep(readingTime);
        System.out.println( String.format("%s finish after reading %dms", name, readingTime));
    }
}