package com.vv.personal.prom.amaterasu.dbo;

import com.vv.personal.prom.artifactory.proto.Make;
import org.junit.Test;

import static com.vv.personal.prom.amaterasu.dbo.MakeDbo.*;
import static org.junit.Assert.*;

/**
 * @author Vivek
 * @since 24/12/20
 */
public class MakeDboTest {

    @Test
    public void testGenerateMakeProto() {
        Integer makeId = generateMakeId("Samsung");
        Make make = generateMakeProto(makeId, "Samsung");
        System.out.println(make);
        assertEquals(765372423, make.getMakeId());
        assertEquals("Samsung", make.getMakeName());
    }

    @Test
    public void testVerifyMakeDetails() {
        assertTrue(verifyMakeDetails("Samsung"));
        assertTrue(verifyMakeDetails("Dell"));
        assertTrue(verifyMakeDetails("Mt. Fujitsu"));

        assertFalse(verifyMakeDetails(""));
        assertFalse(verifyMakeDetails(" "));
    }
}