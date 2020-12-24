package org.ks.trial.morp.amaterasu.config;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Vivek
 * @since 23/12/20
 */
@Configuration
public class AmaterasuConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(AmaterasuConfig.class);

    @Bean(destroyMethod = "shutdown")
    @Qualifier("DbWriterThread")
    public ExecutorService DatabaseWriterService() {
        return Executors.newSingleThreadExecutor();
    }

    @Bean(initMethod = "start")
    @Scope("prototype")
    public StopWatch stopWatch() {
        LOGGER.debug("Obtaining new stopwatch!");
        return new StopWatch();
    }
}
