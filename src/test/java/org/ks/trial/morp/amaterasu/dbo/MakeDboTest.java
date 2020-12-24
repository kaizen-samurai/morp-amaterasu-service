package org.ks.trial.morp.amaterasu.dbo;

import com.vv.personal.prom.artifactory.proto.Make;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.ks.trial.morp.amaterasu.dbo.MakeDbo.*;

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