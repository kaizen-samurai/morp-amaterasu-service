package com.vv.personal.prom.amaterasu.controller;

import com.vv.personal.prom.amaterasu.Util.StringUtil;
import com.vv.personal.prom.amaterasu.config.AmaterasuConfig;
import com.vv.personal.prom.artifactory.proto.Customer;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.vv.personal.prom.amaterasu.constants.Constants.INT_RESPONSE_WONT_PROCESS;
import static com.vv.personal.prom.amaterasu.constants.Constants.WONT_PROCESS_CUSTOMER;
import static com.vv.personal.prom.amaterasu.dbo.CustomerDbo.*;

/**
 * @author Vivek
 * @since 23/12/20
 */
@RestController("AmaterasuController")
@RequestMapping("/prom/amaterasu/")
public class AmaterasuController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AmaterasuController.class);

    @Autowired
    private AmaterasuConfig amaterasuConfig;

    @GetMapping("/add/data/app/customer")
    @ApiOperation(value = "add new customer details via app", hidden = true)
    public Customer addNewCustomerViaApp(@RequestBody Customer customer) {
        //new customer obj should contain the following details: first name, last name and contact numbers
        //the customer id needs to be generated!
        StopWatch stopWatch = amaterasuConfig.stopWatch();
        LOGGER.info("App-based addition of customer data here => {} {} {}", customer.getFirstName(), customer.getLastName(), customer.getContactNumbersList());
        if (!verifyCustomerDetails(customer.getFirstName(), customer.getLastName(), customer.getContactNumbersList())) {
            LOGGER.warn("Invalid customer details supplied, cannot proceed with customer creation in server.");
            return WONT_PROCESS_CUSTOMER;
        }
        Customer newCustomer = createAndSendNewCustomer(customer.getFirstName(), customer.getLastName(), customer.getContactNumbersList());
        stopWatch.stop();
        LOGGER.info("App-based new customer add op over in {}ms, customer id: {}", stopWatch.getTime(TimeUnit.MILLISECONDS), newCustomer.getCustomerId());
        return newCustomer;
    }

    @GetMapping("/add/data/manual/customer")
    @ApiOperation(value = "add new customer details manually")
    public Integer addManualNewCustomer(@RequestParam String firstName,
                                        @RequestParam String lastName,
                                        @RequestParam String contactNumbers) {
        StopWatch stopWatch = amaterasuConfig.stopWatch();
        LOGGER.info("Manually adding customer data here => {} {} {}", firstName, lastName, contactNumbers);
        List<String> numbers = StringUtil.extractStringList(contactNumbers);
        if (numbers.isEmpty()) {
            LOGGER.warn("No contact numbers supplied, cannot proceed with customer creation in server.");
            return INT_RESPONSE_WONT_PROCESS;
        }
        if (!verifyCustomerDetails(firstName, lastName, numbers)) {
            LOGGER.warn("Invalid customer details supplied, cannot proceed with customer creation in server.");
            return INT_RESPONSE_WONT_PROCESS;
        }
        Customer newCustomer = createAndSendNewCustomer(firstName, lastName, numbers);
        stopWatch.stop();
        LOGGER.info("Manual new customer add op over in {}ms, customer id: {}", stopWatch.getTime(TimeUnit.MILLISECONDS), newCustomer.getCustomerId());
        return newCustomer.getCustomerId();
    }

    private Customer createAndSendNewCustomer(String firstName, String lastName, List<String> contactNumbers) {
        StopWatch stopWatch = amaterasuConfig.stopWatch();
        Integer customerId = generateCustomerId(firstName, lastName, contactNumbers);
        Customer newCustomer = generateCustomerProto(customerId, firstName, lastName, contactNumbers);
        stopWatch.stop();
        LOGGER.info("Created new customer proto in {}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));

        if (sendNewCustomerToDatabase(newCustomer)) {
            LOGGER.info("Successfully saved new customer in server-DB");
            return newCustomer;
        }
        LOGGER.error("Failed to save new customer {} in server-DB", newCustomer);
        return WONT_PROCESS_CUSTOMER;
    }

    private Boolean sendNewCustomerToDatabase(Customer newCustomer) {
        StopWatch stopWatch = amaterasuConfig.stopWatch();
        stopWatch.stop();
        LOGGER.info("DB operation completed in {}s", stopWatch.getTime(TimeUnit.SECONDS));
        return true;
    }
}
