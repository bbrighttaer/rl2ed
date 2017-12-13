package com.bbrighttaer.rl.alg;

import ch.qos.logback.classic.Logger;
import com.bbrighttaer.rl.api.IAlgorithm;
import com.bbrighttaer.rl.api.IScoreListener;
import com.bbrighttaer.rl.api.MDP;
import com.bbrighttaer.rl.api.TransitionReply;
import com.bbrighttaer.rl.prob.bd.Bandit;
import com.bbrighttaer.rl.util.PlotUtil;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class SimpleBanditAlg implements IAlgorithm<IScoreListener> {
    private static Logger log = (Logger) LoggerFactory.getLogger(SimpleBanditAlg.class);
    private IScoreListener listener;
    private int indvSteps;
    private int numActions;
    private int numStates;
    private int timeSteps;

    public SimpleBanditAlg(int timeSteps, int indvStateItr, int numActions, int numStates) {
        this.indvSteps = indvStateItr;
        this.numActions = numActions;
        this.numStates = numStates;
        this.timeSteps = timeSteps;
    }

    public void execute(MDP mdp) {
        Random r = new Random();
        Bandit banditMDP = (Bandit) mdp;
        double epsilon = 0.1;
        int state;

        INDArray Q_a;
        INDArray N_a;
        Q_a = Nd4j.zeros(numActions, numStates);
        N_a = Nd4j.zeros(numActions, numStates);
        INDArray avgR;
        avgR = Nd4j.zeros(timeSteps, 1);

        for (int t = 0; t < timeSteps; t++) {
            double rewardSum = 0;
            for (int s = 0; s < numStates; s++) {
                state = s;
                int count = 0;
                rewardSum += Nd4j.sum(Q_a.getColumn(s)).getDouble(0);
                while (count < indvSteps) {
                    log.info("Individual step: " + (count++));

                    //e-greedy
                    int action = (r.nextGaussian() < epsilon) ? banditMDP.getRandomAction(state) : banditMDP.getArgMaxQ(state);

                    //transition to next state
                    TransitionReply<Integer> transition = mdp.doTransition(state, action);
                    double reward = transition.getReward();

                    //back-ups
                    N_a.putScalar(action, s, N_a.getInt(action, s) + 1);
                    double alpha = 0.1;// (1 / N_a.getInt(action, state));
                    double newQ = Q_a.getDouble(action, s) +
                            alpha * (reward - Q_a.getDouble(action, s));
                    Q_a.putScalar(new int[]{action, s}, newQ);

                    //callback
                    listener.acceptValue(Q_a.dup());
                }
                log.info("Actual state-action values: " + banditMDP.getStateActionVals().getRow(state));
            }
            avgR.putScalar(t, 0, rewardSum / (numStates * numActions));
            log.info("------- timestep " + t + " ---------");
        }
        log.info("Computed matrix:\n" + Q_a.transpose());
        log.info("Actual matrix:\n" + banditMDP.getStateActionVals());
        log.info("Avg reward: " + avgR);
        PlotUtil.createChart(avgR.transpose(), 0, 3, "Value", "Bandit");

    }

    public void addScoreListener(IScoreListener listener) {
        this.listener = listener;
    }
}
