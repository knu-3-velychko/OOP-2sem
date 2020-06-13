package com.knu.task8;

import java.time.LocalDateTime;
import java.util.concurrent.Delayed;

class StopRequestTask extends Task<Void> {

    StopRequestTask() {
        super(LocalDateTime.now(), () -> null, 0);
    }

    @Override
    public int compareTo(Delayed o) {
        return -1; // Highest priority
    }
}