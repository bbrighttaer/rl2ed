package com.bbrighttaer.test;

import ch.qos.logback.classic.Logger;
import junit.framework.TestCase;
import org.slf4j.LoggerFactory;

public class SampleTest extends TestCase {
    public void testLogging(){
        Logger log = (Logger) LoggerFactory.getLogger(SampleTest.class);
        log.info("Logging test");
    }
}
