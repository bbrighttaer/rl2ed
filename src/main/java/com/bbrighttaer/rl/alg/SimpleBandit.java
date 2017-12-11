package com.bbrighttaer.rl.alg;

import ch.qos.logback.classic.Logger;
import com.bbrighttaer.rl.api.IAlgorithm;
import com.bbrighttaer.rl.api.IScoreListener;
import com.bbrighttaer.rl.api.TransitionReply;
import com.bbrighttaer.rl.prob.bd.Bandit;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class SimpleBandit implements IAlgorithm<IScoreListener> {
    private static Logger log = (Logger) LoggerFactory.getLogger(SimpleBandit.class);
    private IScoreListener listener;
    private int timesteps;
    private int state;
    private int numActions;
    private int numStates;

    public SimpleBandit(int timesteps, int state, int numActions, int numStates) {
        this.timesteps = timesteps;
        this.state = state;
        this.numActions = numActions;
        this.numStates = numStates;
    }

    @Override
    public void execute() {
        Random r = new Random();
        Bandit bandit = new Bandit(numActions, numStates);
        double epsilon = 0.01;
        int count = 0;

        INDArray Q_a;
        INDArray N_a;
        Q_a = Nd4j.zeros(numActions, 1);
        N_a = Nd4j.zeros(numActions, 1);

        while (count < timesteps) {
            log.info("Timestep: " + (count++));
            //e-greedy
            int action = (r.nextGaussian() < epsilon) ? bandit.getRandomAction(state) : bandit.getArgMaxQ(state);
            //transition to next state
            TransitionReply<Integer> transition = bandit.doTransition(state, action);
            double reward = transition.getReward();
            //int s_prime = transition.getState();
            //back-ups
            N_a.putScalar(action, 0, N_a.getInt(action, 0) + 1);
            double newQ = Q_a.getDouble(action, 0) +
                    (1 / N_a.getInt(action, 0)) * (reward - Q_a.getDouble(action, 0));
            Q_a.putScalar(new int[]{action, 0}, newQ);

            //callback
            listener.acceptValue(Q_a.dup());
        }
        log.info("Actual state-action values: " + bandit.getStateActionVals().getRow(state));
    }

    @Override
    public void addScoreListener(IScoreListener listener) {
        this.listener = listener;
    }
}
