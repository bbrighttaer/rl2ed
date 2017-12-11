package com.bbrighttaer.rl.prob.bd;

import com.bbrighttaer.rl.api.StateSpace;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class BanditStates implements StateSpace<INDArray> {
    private INDArray states;

    public BanditStates(int stateSize) {
        createStaes(stateSize);
    }

    private void createStaes(int size){
        states = Nd4j.create(1, size);
        for(int s=0; s<size; s++){
            states.putScalar(new int[]{0,s},s);
        }
    }

    public String getName() {
        return "K-arm Bandit State Space";
    }

    public INDArray getStateSpace() {
        return states;
    }
}
