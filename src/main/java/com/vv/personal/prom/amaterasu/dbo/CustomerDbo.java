package com.vv.personal.prom.amaterasu.dbo;

import com.vv.personal.prom.artifactory.proto.Customer;

import java.util.List;
import java.util.Objects;

/**
 * @author Vivek
 * @since 23/12/20
 */
public class CustomerDbo {

    public static Integer generateCustomerId(String firstName, String lastName, List<String> contactNumbers) {
        return Math.abs(Objects.hash(firstName, lastName, contactNumbers));
    }

    public static Customer generateCustomerProto(Integer customerId, String firstName, String lastName, List<String> contactNumbers) {
        return Customer.newBuilder()
                .setCustomerId(customerId)
                .setFirstName(firstName)
                .setLastName(lastName)
                .addAllContactNumbers(contactNumbers)
                .build();
    }

    public static boolean verifyCustomerDetails(String firstName, String lastName, List<String> contactNumbers) {
        return isValidName(firstName)
                && isValidName(lastName)
                && contactNumbers.size() == contactNumbers.stream().filter(contact -> contact.matches("[0-9]{10}+")).count();
    }

    public static boolean isValidName(String name) {
        return name.strip().matches("[a-zA-Z .]+");
    }

}
