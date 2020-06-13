package com.knu.task8;

import java.util.concurrent.*;

class TaskFuture<T> implements Future<T> {
    private final Task<T> task;

    TaskFuture(Task<T> task) {
        this.task = task;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return task.cancel();
    }

    @Override
    public boolean isCancelled() {
        return task.getState() == State.CANCELLED;
    }

    @Override
    public boolean isDone() {
        return isCancelled() || task.getState() == State.SUCCESS || task.getState() == State.ERROR;
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        State state = task.waitFinalState();
        switch (state) {
            case CANCELLED:
            case ERROR:
            case SUCCESS:
                return getResultAccordingFinalState(state);
            default:
                throw new IllegalStateException("Unexpected behaviour: state " + state + " must not occur here", null);
        }
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        State state = task.waitFinalState(timeout, unit);
        switch (state) {
            case CANCELLED:
            case ERROR:
            case SUCCESS:
                return getResultAccordingFinalState(state);
            default:
                throw new TimeoutException("Wait timeout");
        }
    }

    private T getResultAccordingFinalState(State finalState) throws ExecutionException {
        switch (finalState) {
            case CANCELLED:
                throw new CancellationException("Task was cancelled");
            case ERROR:
                throw new ExecutionException("Error while executing task", task.getError());
            case SUCCESS:
                return task.getResult();
            default:
                throw new IllegalStateException("Unexpected state " + finalState + ". Expected one of final states");
        }
    }
}