package com.bbrighttaer.rl.prob.bd;

import com.bbrighttaer.rl.api.MDP;
import com.bbrighttaer.rl.api.StateSpace;
import com.bbrighttaer.rl.api.TransitionReply;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.ops.transforms.Transforms;

import java.util.Random;

public class Bandit implements MDP<Integer, Integer, BanditActions> {

    private BanditActions banditActions;
    private BanditStates banditStates;
    private INDArray stateActionVals;
    private int stateSize;
    private int arms;
    private INDArray transProbMatrix;
    private Random r = new Random();

    public Bandit(int k, int statesSize) {
        this.stateSize = statesSize;
        this.arms = k;
        this.banditStates = new BanditStates(statesSize);
        this.banditActions = new BanditActions(k);
        this.stateActionVals = Nd4j.create(statesSize, k);
        createStateActionValues();
        createTransMatrix();
    }

    private void createStateActionValues() {
        Random r = new Random();
        for (int s = 0; s < stateSize; s++) {
            for (int a = 0; a < arms; a++) {
                this.stateActionVals.putScalar(new int[]{s, a}, r.nextDouble());
            }
        }
    }

    private void createTransMatrix() {
        this.transProbMatrix = Nd4j.create(stateSize, stateSize);
        for (int s = 0; s < stateSize; s++) {
            INDArray row = Nd4j.create(1, stateSize);
            for (int i = 0; i < stateSize; i++) {
                if (s != i) {
                    row.putScalar(0, i, Math.random());
                } else
                    row.putScalar(0, i, 0);
            }
            transProbMatrix.putRow(s, Transforms.softmax(row));
        }
    }

    public StateSpace<INDArray> getStateSpace() {
        return banditStates;
    }

    public BanditActions getActionSpace() {
        return banditActions;
    }

    public Integer reset() {
        return this.banditStates.getStateSpace().getInt(0, ((int) (Math.random() * stateSize)));
    }

    public TransitionReply<Integer> doTransition(Integer state, Integer action) {
        INDArray row = transProbMatrix.getRow(state);
        int s_prime = 0;
        double random = r.nextGaussian();
        for (int i = 0; i < row.columns(); i++) {
            if (random < row.getDouble(0, i)) {
                s_prime = i;
                break;
            }
        }
        double reward = stateActionVals.getDouble(state, action);
        return new BanditTransitionReply(s_prime, reward);
    }

    public TransitionReply<Integer> doTransition(Integer action) {
        return doTransition(0, action);
    }

    public Integer getRandomAction(Integer state) {
        return this.banditActions.randomAction();
    }

    public Integer getArgMaxQ(Integer state) {
        INDArray row = stateActionVals.getRow(state);
        int action = 0;
        double q = 0;
        for (int a = 0; a < row.columns(); a++) {
            double v = row.getDouble(0, a);
            if (v >= q) {
                q = v;
                action = a;
            }
        }
        return action;
    }

    public double getMaxQ(Integer state) {
        return stateActionVals.getRow(state).maxNumber().doubleValue();
    }

    public INDArray getTransProbMatrix() {
        return transProbMatrix;
    }

    public INDArray getStateActionVals() {
        return stateActionVals;
    }
}
