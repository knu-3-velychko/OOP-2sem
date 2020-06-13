package com.knu.task8;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Callable;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

class Task<T> implements Delayed {

    private static final int TIMEOUT_INFINITE = -1;

    private final LocalDateTime time;
    private final Callable<T> callable;
    private final long serialIndex;
    private final Object doneMonitor = new Object();
    private AtomicReference<State> state = new AtomicReference<>(State.PENDING);
    private AtomicReference<T> result = new AtomicReference<>();
    private AtomicReference<Throwable> error = new AtomicReference<>();

    Task(LocalDateTime time, Callable<T> callable, long serialIndex) {
        if (time == null) {
            throw new IllegalArgumentException("Param 'time' must not be null");
        }
        if (callable == null) {
            throw new IllegalArgumentException("Param 'callable' must not be null");
        }
        this.time = time;
        this.callable = callable;
        this.serialIndex = serialIndex;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        LocalDateTime nowTime = LocalDateTime.now();
        long millisToStart = ChronoUnit.MILLIS.between(nowTime, time);
        return unit.convert(millisToStart, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        if (o instanceof Task) {
            int result = time.compareTo(((Task) o).time);
            // If time's are equal we do compare serial numbers to keep task order correct.
            // Note: it is actual only in case of single scheduler's worker thread.
            return result == 0 ?
                    Long.compare(this.serialIndex, ((Task) o).serialIndex) :
                    result;
        } else {
            throw new IllegalArgumentException("Expected object of type 'Task'");
        }
    }

    void execute() {
        try {
            if (tryChangeState(State.PENDING, State.EXECUTING)) {
                result.set(callable.call());
                tryChangeState(State.EXECUTING, State.SUCCESS);
            }
        } catch (Throwable e) {
            if (tryChangeState(State.EXECUTING, State.ERROR)) {
                error.set(e);
            }
        }
    }

    boolean cancel() {
        return tryChangeState(State.PENDING, State.CANCELLED);
    }

    State getState() {
        return state.get();
    }

    State waitFinalState() throws InterruptedException {
        return waitFinalStateLocal(TIMEOUT_INFINITE, null);
    }

    State waitFinalState(long timeout, TimeUnit unit) throws InterruptedException {
        if (timeout < 0) {
            throw new IllegalArgumentException("Expected timeout >= 0, actual: " + timeout);
        }
        return waitFinalStateLocal(timeout, unit);
    }

    Throwable getError() {
        return error.get();
    }

    T getResult() {
        return result.get();
    }

    private State waitFinalStateLocal(long timeout, TimeUnit unit) throws InterruptedException {
        switch (state.get()) {
            case SUCCESS:
            case CANCELLED:
            case ERROR:
                // Task in one of 'final' state.
                return state.get();

            default: {
                // Task is not in 'final' state. Do wait.
                synchronized (doneMonitor) {
                    if (timeout >= 0) {
                        doneMonitor.wait(unit.toMillis(timeout));
                    } else {
                        doneMonitor.wait();
                    }
                }
                return state.get();
            }
        }
    }

    private boolean tryChangeState(State expectedState, State newState) {
        if (state.compareAndSet(expectedState, newState)) {
            if (newState == State.ERROR || newState == State.SUCCESS || newState == State.CANCELLED) {
                synchronized (doneMonitor) {
                    doneMonitor.notifyAll();
                }
            }
            return true;
        }
        return false;
    }
}