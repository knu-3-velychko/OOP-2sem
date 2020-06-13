package com.knu.task7;

import java.util.concurrent.Semaphore;

public class ReaderWriterPattern {
    private Semaphore serviceQueue;
    private Semaphore readCountAccess;
    private Semaphore write;
    private int readCount;

    public ReaderWriterPattern() {
        readCount = 0;
        serviceQueue = new Semaphore(1, true);
        readCountAccess = new Semaphore(1, true);
        write = new Semaphore(1, true);
    }

    public void lockReader() {
        try {
            serviceQueue.acquire();
            readCountAccess.acquire();
            try {
                if (readCount == 0)
                    write.acquire();
                readCount++;
            } catch (InterruptedException e) {
            } finally {
                readCountAccess.release();
            }
        } catch (InterruptedException e) {
        } finally {
            serviceQueue.release();
        }
    }

    public void unlockReader() {
        try {
            readCountAccess.acquire();
            readCount--;
            if (readCount == 0) {
                write.release();
            }
            readCountAccess.release();
        } catch (InterruptedException e) {
        }
    }

    public void lockWriter() {
        try {
            serviceQueue.acquire();
            write.acquire();
        } catch (InterruptedException e) {
        } finally {
            serviceQueue.release();
        }
    }

    public void unlockWriter() {
        write.release();
    }
}