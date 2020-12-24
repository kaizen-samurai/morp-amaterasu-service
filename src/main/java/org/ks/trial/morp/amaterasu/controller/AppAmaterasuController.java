package org.ks.trial.morp.amaterasu.controller;

import com.vv.personal.prom.artifactory.proto.Customer;
import com.vv.personal.prom.artifactory.proto.Make;
import com.vv.personal.prom.artifactory.proto.Problems;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.time.StopWatch;
import org.ks.trial.morp.amaterasu.constants.Constants;
import org.ks.trial.morp.amaterasu.dbo.CustomerDbo;
import org.ks.trial.morp.amaterasu.dbo.MakeDbo;
import org.ks.trial.morp.amaterasu.dbo.ProblemDbo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Vivek
 * @since 23/12/20
 */
@RestController("AppAmaterasuController")
@RequestMapping("/morp/amaterasu/app")
public class AppAmaterasuController extends AbstractAmaterasu {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppAmaterasuController.class);

    @GetMapping("/add/data/customer")
    @ApiOperation(value = "add new customer details via app", hidden = true)
    public Customer addNewCustomerViaApp(@RequestBody Customer customer) {
        //new customer obj should contain the following details: first name, last name and contact numbers
        //the customer id needs to be generated!
        StopWatch stopWatch = amaterasuConfig.stopWatch();
        LOGGER.info("App-based addition of customer data here => '{}' '{}' '{}'", customer.getFirstName(), customer.getLastName(), customer.getContactNumbersList());
        if (!CustomerDbo.verifyCustomerDetails(
                customer.getFirstName(),
                customer.getLastName(),
                customer.getContactNumbersList(),
                customer.getCompany().getCompanyName())) {
            LOGGER.warn("Invalid customer details supplied, cannot proceed with customer creation in server.");
            return Constants.WONT_PROCESS_CUSTOMER;
        }
        Customer newCustomer = createAndSendNewCustomer(customer.getFirstName(), customer.getLastName(), customer.getContactNumbersList(),
                customer.getCompany().getCompanyName());
        stopWatch.stop();
        LOGGER.info("App-based new customer add op over in {}ms, customer id: {}", stopWatch.getTime(TimeUnit.MILLISECONDS), newCustomer.getCustomerId());
        stopWatch = null;
        return newCustomer;
    }

    @GetMapping("/add/data/make")
    @ApiOperation(value = "add new make details via app", hidden = true)
    public Make addNewMakeViaApp(@RequestBody Make make) {
        StopWatch stopWatch = amaterasuConfig.stopWatch();
        LOGGER.info("App-based addition of make data here => '{}'", make.getMakeName());
        if (!MakeDbo.verifyMakeDetails(make.getMakeName())) {
            LOGGER.warn("Invalid make details supplied, cannot proceed with make creation in server.");
            return Constants.WONT_PROCESS_MAKE;
        }
        Make newMake = createAndSendNewMake(make.getMakeName());
        stopWatch.stop();
        LOGGER.info("App-based new make add op over in {}ms, make id: {}", stopWatch.getTime(TimeUnit.MILLISECONDS), newMake.getMakeId());
        stopWatch = null;
        return newMake;
    }

    @GetMapping("/add/data/problems")
    @ApiOperation(value = "add new problems via app", hidden = true)
    public Problems addNewProblemsViaApp(@RequestBody Problems problems) {
        StopWatch stopWatch = amaterasuConfig.stopWatch();
        LOGGER.info("App-based addition of problems data here => '{}'", problems.getProblemsList());

        List<String> newProblemList = ProblemDbo.verifyProblemDetailsProto(new HashSet<>(problems.getProblemsList()));
        if (newProblemList.isEmpty()) {
            LOGGER.warn("Invalid problem details supplied, cannot proceed with problem creation in server.");
            return Constants.WONT_PROCESS_PROBLEMS;
        }
        Problems newProblems = createAndSendNewProblems(newProblemList);
        stopWatch.stop();
        LOGGER.info("App-based new problems add op over in {}ms, make id: {}", stopWatch.getTime(TimeUnit.MILLISECONDS), newProblems.getProblemsList());
        stopWatch = null;
        return newProblems;
    }
}
