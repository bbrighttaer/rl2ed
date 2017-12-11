package com.bbrighttaer.rl.api;

public interface TransitionReply<T> {
    T getState();

    double getReward();

    boolean isDone();
}
