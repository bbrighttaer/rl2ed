package com.bbrighttaer.rl.api;

public interface MDP<A, S, AS extends ActionSpace<A>> {
    StateSpace getStateSpace();

    AS getActionSpace();

    S reset();

    TransitionReply<S> doTransition(S state, A action);

    TransitionReply<S> doTransition(A action);

    A getRandomAction(S state);

    A getArgMaxQ(S state);

    double getMaxQ(S state);
}
