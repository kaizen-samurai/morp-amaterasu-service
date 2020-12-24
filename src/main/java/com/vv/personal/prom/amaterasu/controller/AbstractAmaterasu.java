package com.vv.personal.prom.amaterasu.controller;

import com.vv.personal.prom.amaterasu.config.AmaterasuConfig;
import com.vv.personal.prom.artifactory.proto.Customer;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.vv.personal.prom.amaterasu.constants.Constants.WONT_PROCESS_CUSTOMER;
import static com.vv.personal.prom.amaterasu.dbo.CustomerDbo.*;

/**
 * @author Vivek
 * @since 24/12/20
 */
@Component
public abstract class AbstractAmaterasu {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAmaterasu.class);

    @Autowired
    protected AmaterasuConfig amaterasuConfig;

    protected Customer createAndSendNewCustomer(String firstName, String lastName, List<String> contactNumbers,
                                                String companyName) {
        StopWatch stopWatch = amaterasuConfig.stopWatch();
        Integer customerId = generateCustomerId(firstName, lastName, contactNumbers);
        Integer companyId = generateCompanyId(companyName);
        Customer newCustomer = generateCustomerProto(customerId, firstName, lastName, contactNumbers, companyId, companyName);
        stopWatch.stop();
        LOGGER.info("Created new customer proto in {}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));

        if (sendNewCustomerToDatabase(newCustomer)) {
            LOGGER.info("Successfully saved new customer in server-DB");
            return newCustomer;
        }
        LOGGER.error("Failed to save new customer {} in server-DB", newCustomer);
        return WONT_PROCESS_CUSTOMER;
    }

    protected Boolean sendNewCustomerToDatabase(Customer newCustomer) {
        StopWatch stopWatch = amaterasuConfig.stopWatch();
        stopWatch.stop();
        LOGGER.info("DB operation completed in {}s", stopWatch.getTime(TimeUnit.SECONDS));
        return true;
    }
}