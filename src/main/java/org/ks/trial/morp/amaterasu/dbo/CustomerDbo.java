package org.ks.trial.morp.amaterasu.dbo;

import com.vv.personal.prom.artifactory.proto.Company;
import com.vv.personal.prom.artifactory.proto.Customer;
import org.ks.trial.morp.amaterasu.util.StringUtil;

import java.util.List;

/**
 * @author Vivek
 * @since 23/12/20
 */
public class CustomerDbo extends AbstractDbo {

    public static Integer generateCustomerId(String firstName, String lastName, List<String> contactNumbers) {
        return generateId(firstName, lastName, contactNumbers);
    }

    public static Integer generateCompanyId(String companyName) {
        return generateId(companyName);
    }

    public static Customer generateCustomerProto(Integer customerId, String firstName, String lastName, List<String> contactNumbers,
                                                 Integer companyId, String companyName) {
        return Customer.newBuilder()
                .setCustomerId(customerId)
                .setFirstName(firstName)
                .setLastName(lastName)
                .addAllContactNumbers(contactNumbers)
                .setCompany(generateCompany(companyId, companyName))
                .build();
    }

    public static Company generateCompany(int companyId, String companyName) {
        return Company.newBuilder()
                .setCompanyId(companyId)
                .setCompanyName(companyName)
                .build();
    }

    public static boolean verifyCustomerDetails(String firstName, String lastName, List<String> contactNumbers, String companyName) {
        return isValidName(firstName)
                && isValidName(lastName)
                && contactNumbers.size() == contactNumbers.stream().filter(contact -> contact.matches("[0-9]{10}+")).count()
                && isValidCompanyName(companyName);
    }

    public static boolean isValidName(String name) {
        return name.strip().matches("[a-zA-Z .]+");
    }

    public static boolean isValidCompanyName(String name) {
        return StringUtil.isValidInput(name);
    }
}
