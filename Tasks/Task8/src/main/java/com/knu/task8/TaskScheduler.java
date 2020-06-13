package com.knu.task8;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TaskScheduler {

    private static AtomicLong totalTasksScheduled = new AtomicLong();
    private final DelayQueue<Task> queue = new DelayQueue<>();
    private final Object lock = new Object();
    private volatile boolean running;
    private AtomicInteger runningThreads = new AtomicInteger(0);
    private int threadsCount = 0;

    public void start(int threadsCount) {
        if (threadsCount <= 0) {
            throw new IllegalArgumentException("Threads count must be positive");
        }

        synchronized (lock) {
            if (running) {
                throw new RuntimeException("Scheduler is already running");
            }
            this.threadsCount = threadsCount;
            for (int i = 0; i < threadsCount; ++i) {
                new Thread(this::doInThread, "task-scheduler-worker-thread-" + i).start();
                runningThreads.incrementAndGet();
            }
            running = true;
        }
    }

    public void stop(boolean waitTermination) throws InterruptedException {
        synchronized (lock) {
            if (running) {
                try {
                    // Add special marks to queue to stop the worker threads.
                    for (int i = 0; i < threadsCount; ++i) {
                        queue.add(new StopRequestTask());
                    }
                    if (waitTermination) {
                        while (runningThreads.get() > 0) {
                            Thread.sleep(1);
                        }
                    }
                } finally {
                    running = false;
                }
            }
        }
    }

    public <T> Future<T> schedule(LocalDateTime time, Callable<T> callable) {
        Task<T> task = new Task<>(time, callable, totalTasksScheduled.incrementAndGet());
        queue.add(task);
        return new TaskFuture<>(task);
    }

    private void doInThread() {
        try {
            while (true) {
                try {
                    Task task = queue.take();
                    if (task instanceof StopRequestTask) {
                        // We've got a special 'stop working' mark. Exiting processing loop now.
                        break;
                    }
                    task.execute();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Set interrupt flag
                    break;
                }
            }
        } finally {
            runningThreads.decrementAndGet();
        }
    }
}