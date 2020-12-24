package org.ks.trial.morp.amaterasu.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Vivek
 * @since 23/12/20
 */
@RunWith(JUnit4.class)
public class StringUtilTest {

    @Test
    public void testExtractStringList() {
        List<String> result = StringUtil.extractStringList("abcd, ejkeijF34  !   ");
        System.out.println(result);
        assertEquals(2, result.size());
        assertEquals("abcd", result.get(0));
        assertEquals("ejkeijF34  !", result.get(1));

        assertTrue(StringUtil.extractStringList("").isEmpty());
        assertTrue(StringUtil.extractStringList(" ").isEmpty());
    }

    @Test
    public void testExtractStringSet() {
        Set<String> result = StringUtil.extractStringSet("abcd, ejkeijF34  !   , lopez, lopez , spaska");
        System.out.println(result);
        assertEquals(4, result.size());
        assertTrue(result.contains("abcd"));
        assertTrue(result.contains("ejkeijF34  !"));
        assertTrue(result.contains("lopez"));

        assertTrue(StringUtil.extractStringList("").isEmpty());
        assertTrue(StringUtil.extractStringList(" ").isEmpty());
    }
}