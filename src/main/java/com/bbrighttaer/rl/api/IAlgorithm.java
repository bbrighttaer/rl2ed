package com.bbrighttaer.rl.api;

import java.util.EventListener;

public interface IAlgorithm<L extends EventListener> {
    void execute();

    void addScoreListener(L listeners);
}
