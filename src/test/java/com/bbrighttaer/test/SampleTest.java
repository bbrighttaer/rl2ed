package com.bbrighttaer.test;

import ch.qos.logback.classic.Logger;
import com.bbrighttaer.rl.util.PlotUtil;
import junit.framework.TestCase;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.slf4j.LoggerFactory;

public class SampleTest extends TestCase {
    public void testLogging() {
        Logger log = (Logger) LoggerFactory.getLogger(SampleTest.class);
        log.info("Logging test");
    }

    public void testPlotUtil() {
        INDArray nd6 = Nd4j.create(new float[]{1, 2, 3}, new int[]{1, 3});
        PlotUtil.createChart(nd6, 0, 5, "score", "sometitle");
        while (true) ;
    }
}
