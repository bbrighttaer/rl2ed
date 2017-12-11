package com.bbrighttaer.rl.prob.bd;

import com.bbrighttaer.rl.api.ActionSpace;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.util.ArrayList;
import java.util.List;

public class BanditActions implements ActionSpace<Integer> {

    private int k;
    private INDArray actions;

    public BanditActions(int k) {
        this.k = k;
        this.actions = Nd4j.create(1, k);
        createArms();
    }

    private void createArms() {
        for (int i = 0; i < k; i++) {
            actions.putScalar(new int[]{0, i}, i);
        }
    }

    public Integer randomAction() {
        return actions.getInt(0, (int) (Math.random()*k));
    }

    public Integer noOp() {
        return null;
    }

    public List<Integer> getLegalActions() {
        List<Integer> actionsList = new ArrayList<>();
        for (int a = 0; a < k; a++) {
            actionsList.add(actions.getInt(0, a));
        }
        return actionsList;
    }
}
