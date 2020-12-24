package com.vv.personal.prom.amaterasu.controller;

import com.vv.personal.prom.amaterasu.Util.StringUtil;
import com.vv.personal.prom.artifactory.proto.Customer;
import com.vv.personal.prom.artifactory.proto.Make;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.vv.personal.prom.amaterasu.constants.Constants.INT_RESPONSE_WONT_PROCESS;
import static com.vv.personal.prom.amaterasu.dbo.CustomerDbo.verifyCustomerDetails;
import static com.vv.personal.prom.amaterasu.dbo.MakeDbo.verifyMakeDetails;

/**
 * @author Vivek
 * @since 24/12/20
 */
@RestController("ManualAmaterasuController")
@RequestMapping("/prom/amaterasu/manual")
public class ManualAmaterasuController extends AbstractAmaterasu {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManualAmaterasuController.class);

    @GetMapping("/add/data/customer")
    @ApiOperation(value = "add new customer details manually")
    public Integer addManualNewCustomer(@RequestParam String firstName,
                                        @RequestParam String lastName,
                                        @RequestParam String contactNumbers,
                                        @RequestParam String companyName) {
        StopWatch stopWatch = amaterasuConfig.stopWatch();
        LOGGER.info("Manually adding customer data here => '{}' '{}' '{}' '{}'", firstName, lastName, contactNumbers, companyName);
        List<String> numbers = StringUtil.extractStringList(contactNumbers);
        if (numbers.isEmpty()) {
            LOGGER.warn("No contact numbers supplied, cannot proceed with customer creation in server.");
            return INT_RESPONSE_WONT_PROCESS;
        }
        if (!verifyCustomerDetails(firstName, lastName, numbers, companyName)) {
            LOGGER.warn("Invalid customer details supplied, cannot proceed with customer creation in server.");
            return INT_RESPONSE_WONT_PROCESS;
        }
        Customer newCustomer = createAndSendNewCustomer(firstName, lastName, numbers, companyName);
        stopWatch.stop();
        LOGGER.info("Manual new customer add op over in {}ms, customer id: {}", stopWatch.getTime(TimeUnit.MILLISECONDS), newCustomer.getCustomerId());
        stopWatch = null;
        return newCustomer.getCustomerId();
    }

    @GetMapping("/add/data/make")
    @ApiOperation(value = "add new make details manually")
    public Integer addManualNewMake(@RequestParam String makeName) {
        StopWatch stopWatch = amaterasuConfig.stopWatch();
        LOGGER.info("Manually adding make data here => '{}'", makeName);
        if (!verifyMakeDetails(makeName)) {
            LOGGER.warn("Invalid make details supplied, cannot proceed with make creation in server.");
            return INT_RESPONSE_WONT_PROCESS;
        }
        Make newMake = createAndSendNewMake(makeName);
        stopWatch.stop();
        LOGGER.info("Manual new make add op over in {}ms, make id: {}", stopWatch.getTime(TimeUnit.MILLISECONDS), newMake.getMakeId());
        stopWatch = null;
        return newMake.getMakeId();
    }
}
