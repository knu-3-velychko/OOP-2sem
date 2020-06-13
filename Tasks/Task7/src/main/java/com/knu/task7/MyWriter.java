package com.knu.task7;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyWriter implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(MyWriter.class.getName());

    private ReaderWriterPattern safeLock;
    private String name;
    private long writingTime;

    public MyWriter(String name, ReaderWriterPattern lock) {
        this(name, lock, 250L);
    }

    public MyWriter(String name, ReaderWriterPattern lock, long writingTime) {
        this.name = name;
        this.safeLock = lock;
        this.writingTime = writingTime;
    }

    @Override
    public void run() {
        safeLock.lockWriter();
        try {
            write();
        } catch (InterruptedException e) {
            System.out.println( "InterruptedException when writing");
            Thread.currentThread().interrupt();
        } finally {
            safeLock.unlockWriter();
        }
    }

    public void write() throws InterruptedException {
        System.out.println( String.format("%s begin", name));
        Thread.sleep(writingTime);
        System.out.println( String.format("%s finished after writing %dms", name, writingTime));
    }
}