package com.bbrighttaer.test;

import ch.qos.logback.classic.Logger;
import com.bbrighttaer.rl.alg.SimpleBanditAlg;
import com.bbrighttaer.rl.prob.bd.Bandit;
import junit.framework.TestCase;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class BanditTestCase extends TestCase {
    private static Logger log = (Logger) LoggerFactory.getLogger(BanditTestCase.class);

    public void testSimpleBandit() {
        Bandit bandit = new Bandit(10, 5);
        System.out.println("Reset: " + bandit.reset());
        System.out.println("States transition probability matrix\n" + bandit.getTransProbMatrix());
        System.out.println("Legal actions: " + Arrays.toString(bandit.getActionSpace().getLegalActions().toArray()));
        System.out.println("Possible states: " + bandit.getStateSpace().getStateSpace());
        System.out.println("Reward probs: " + bandit.getStateActionVals());
        System.out.println("ArgMaxQ: " + bandit.getArgMaxQ(0));
        System.out.println("MaxQ: " + bandit.getMaxQ(0));
        System.out.println("Random action: " + bandit.getRandomAction(0));
    }

    public void testAlgorithm() {
        int numActions = 10;
        int numStates = 5;
        SimpleBanditAlg simpleBanditAlg = new SimpleBanditAlg(100,10, numActions, numStates);
        Bandit mdp = new Bandit(numActions, numStates);
        simpleBanditAlg.addScoreListener(val -> log.info(val.toString()));
        simpleBanditAlg.execute(mdp);
        while (true) ;
    }
}
