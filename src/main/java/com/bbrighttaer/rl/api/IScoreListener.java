package com.bbrighttaer.rl.api;

import java.util.EventListener;

public interface IScoreListener extends EventListener {
    void acceptValue(Object val);
}
