package com.bbrighttaer.rl.api;

import java.util.List;

public interface ActionSpace<A> {
    A randomAction();
    A noOp();
    List<A> getLegalActions();
}
