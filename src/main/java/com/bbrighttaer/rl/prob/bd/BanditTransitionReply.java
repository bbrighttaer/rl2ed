package com.bbrighttaer.rl.prob.bd;

import com.bbrighttaer.rl.api.TransitionReply;

public class BanditTransitionReply implements TransitionReply<Integer> {
    private int state;
    private double reward;

    public BanditTransitionReply(int state, double reward) {
        this.state = state;
        this.reward = reward;
    }

    @Override
    public Integer getState() {
        return state;
    }

    @Override
    public double getReward() {
        return reward;
    }

    @Override
    public boolean isDone() {
        return false;
    }
}
