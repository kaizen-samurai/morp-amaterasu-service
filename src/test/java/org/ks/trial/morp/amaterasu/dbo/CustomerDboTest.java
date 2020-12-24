package org.ks.trial.morp.amaterasu.dbo;

import com.vv.personal.prom.artifactory.proto.Customer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.ks.trial.morp.amaterasu.dbo.CustomerDbo.*;

/**
 * @author Vivek
 * @since 23/12/20
 */
public class CustomerDboTest {

    @Test
    public void testIsValidName() {
        assertTrue(isValidName("Vivek"));
        assertTrue(isValidName("Vivek V"));
        assertTrue(isValidName("Mr. Vivek"));

        assertFalse(isValidName("Vivek12"));
        assertFalse(isValidName("Vivek@1"));
    }

    @Test
    public void testGenerateCustomerId() {
        List<String> contacts = createDummyContacts();
        int val = generateCustomerId("Vivek", "Uchiha", contacts);
        System.out.println(val);
        assertEquals(1887348836, val);
    }

    @Test
    public void testGenerateCustomerProto() {
        Customer customer = generateCustomerProto(1887348836, "Vivek", "V", createDummyContacts(), 7845465, "AMT");
        System.out.println(customer);
        assertEquals("1234567890", customer.getContactNumbersList().get(0));
        assertEquals(7845465, customer.getCompany().getCompanyId());
    }

    @Test
    public void testVerifyCustomerDetails() {
        List<String> contacts = createDummyContacts();
        assertTrue(verifyCustomerDetails("Vivek", "V", contacts, "AMT"));

        contacts.remove(1);
        contacts.add("123425");
        assertFalse(verifyCustomerDetails("Vivek", "V", contacts, "AMT"));

        contacts.clear();
        contacts.add("123456789a");
        assertFalse(verifyCustomerDetails("Vivek", "V", contacts, "AMT"));

        contacts.clear();
        contacts.add("1232324 34");
        assertFalse(verifyCustomerDetails("Vivek", "V", contacts, "AMT"));

        contacts = createDummyContacts();
        assertFalse(verifyCustomerDetails("Vivek", "", contacts, "AMT"));
        assertFalse(verifyCustomerDetails("Vivek", " ", contacts, "AMT"));
        assertFalse(verifyCustomerDetails("Vivek", " ", contacts, ""));
    }

    private List<String> createDummyContacts() {
        List<String> contacts = new ArrayList<>();
        contacts.add("1234567890");
        contacts.add("1234567860");
        return contacts;
    }
}