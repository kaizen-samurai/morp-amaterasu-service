package com.vv.personal.prom.amaterasu.constants;

import com.vv.personal.prom.artifactory.proto.Customer;
import com.vv.personal.prom.artifactory.proto.Make;
import com.vv.personal.prom.artifactory.proto.Problem;

/**
 * @author Vivek
 * @since 23/12/20
 */
public class Constants {

    public static final String EMPTY_STR = "";
    public static final String SPACE_STR = " ";
    public static final String COMMA_STR = ",";
    public static final String DEFAULT_STR_INVALID = "-1";

    public static final int DEFAULT_INT_INVALID = -1;

    //RESPONSES
    public static final Integer INT_RESPONSE_WONT_PROCESS = -13; //N Proc
    public static final Customer WONT_PROCESS_CUSTOMER = Customer.newBuilder()
            .setCustomerId(DEFAULT_INT_INVALID)
            .setFirstName(DEFAULT_STR_INVALID).setLastName(DEFAULT_STR_INVALID)
            .addContactNumbers(DEFAULT_STR_INVALID)
            .build();
    public static final Make WONT_PROCESS_MAKE = Make.newBuilder()
            .setMakeId(DEFAULT_INT_INVALID)
            .setMakeName(DEFAULT_STR_INVALID)
            .build();
    public static final Problem WONT_PROCESS_PROBLEM = Problem.newBuilder()
            .setProblemId(DEFAULT_INT_INVALID)
            .setProblemName(DEFAULT_STR_INVALID)
            .build();

    //FORMATTERS
    public static final String SWAGGER_UI_URL = "http://%s:%s/swagger-ui/index.html";

    public static final String LOCALHOST = "localhost";
    public static final String LOCAL_SPRING_HOST = "local.server.host";
    public static final String LOCAL_SPRING_PORT = "local.server.port";
}
