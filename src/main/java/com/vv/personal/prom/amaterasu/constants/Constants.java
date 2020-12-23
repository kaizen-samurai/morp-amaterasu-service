package com.vv.personal.prom.amaterasu.constants;

import com.vv.personal.prom.artifactory.proto.Customer;

/**
 * @author Vivek
 * @since 23/12/20
 */
public class Constants {

    public static final String EMPTY_STR = "";
    public static final String SPACE_STR = " ";
    public static final String COMMA_STR = ",";

    //RESPONSES
    public static final Integer INT_RESPONSE_WONT_PROCESS = -13; //N Proc
    public static final Customer WONT_PROCESS_CUSTOMER = Customer.newBuilder()
            .setCustomerId(-1)
            .setFirstName("-1").setLastName("-1")
            .addContactNumbers("-1")
            .build();

    //FORMATTERS
    public static final String SWAGGER_UI_URL = "http://%s:%s/swagger-ui/index.html";

    public static final String LOCALHOST = "localhost";
    public static final String LOCAL_SPRING_HOST = "local.server.host";
    public static final String LOCAL_SPRING_PORT = "local.server.port";
}
