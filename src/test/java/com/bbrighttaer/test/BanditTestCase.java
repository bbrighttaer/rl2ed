package com.bbrighttaer.test;

import ch.qos.logback.classic.Logger;
import com.bbrighttaer.rl.alg.SimpleBandit;
import com.bbrighttaer.rl.prob.bd.Bandit;
import junit.framework.TestCase;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class BanditTestCase extends TestCase {
    private static Logger log = (Logger) LoggerFactory.getLogger(BanditTestCase.class);

    public void testSimpleBandit(){
        Bandit bandit = new Bandit(10, 5);
        System.out.println("Reset: "+bandit.reset());
        System.out.println("States transition probability matrix\n"+bandit.getTransProbMatrix());
        System.out.println("Legal actions: "+Arrays.toString(bandit.getActionSpace().getLegalActions().toArray()));
        System.out.println("Possible states: "+bandit.getStateSpace().getStateSpace());
        System.out.println("Reward probs: "+bandit.getStateActionVals());
        System.out.println("ArgMaxQ: "+bandit.getArgMaxQ(0));
        System.out.println("MaxQ: "+bandit.getMaxQ(0));
        System.out.println("Random action: "+bandit.getRandomAction(0));
    }

    public void testAlgorithm(){
        SimpleBandit simpleBandit = new SimpleBandit(100, 1, 10, 10);
        simpleBandit.addScoreListener(val -> log.info(val.toString()));
        simpleBandit.execute();
    }
}
