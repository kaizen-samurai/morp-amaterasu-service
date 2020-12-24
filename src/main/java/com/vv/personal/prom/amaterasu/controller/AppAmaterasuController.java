package com.vv.personal.prom.amaterasu.controller;

import com.vv.personal.prom.artifactory.proto.Customer;
import com.vv.personal.prom.artifactory.proto.Make;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

import static com.vv.personal.prom.amaterasu.constants.Constants.WONT_PROCESS_CUSTOMER;
import static com.vv.personal.prom.amaterasu.dbo.CustomerDbo.verifyCustomerDetails;

/**
 * @author Vivek
 * @since 23/12/20
 */
@RestController("AppAmaterasuController")
@RequestMapping("/prom/amaterasu/app")
public class AppAmaterasuController extends AbstractAmaterasu {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppAmaterasuController.class);

    @GetMapping("/add/data/customer")
    @ApiOperation(value = "add new customer details via app", hidden = true)
    public Customer addNewCustomerViaApp(@RequestBody Customer customer) {
        //new customer obj should contain the following details: first name, last name and contact numbers
        //the customer id needs to be generated!
        StopWatch stopWatch = amaterasuConfig.stopWatch();
        LOGGER.info("App-based addition of customer data here => {} {} {}", customer.getFirstName(), customer.getLastName(), customer.getContactNumbersList());
        if (!verifyCustomerDetails(
                customer.getFirstName(),
                customer.getLastName(),
                customer.getContactNumbersList(),
                customer.getCompany().getCompanyName())) {
            LOGGER.warn("Invalid customer details supplied, cannot proceed with customer creation in server.");
            return WONT_PROCESS_CUSTOMER;
        }
        Customer newCustomer = createAndSendNewCustomer(customer.getFirstName(), customer.getLastName(), customer.getContactNumbersList(),
                customer.getCompany().getCompanyName());
        stopWatch.stop();
        LOGGER.info("App-based new customer add op over in {}ms, customer id: {}", stopWatch.getTime(TimeUnit.MILLISECONDS), newCustomer.getCustomerId());
        stopWatch = null;
        return newCustomer;
    }

}
