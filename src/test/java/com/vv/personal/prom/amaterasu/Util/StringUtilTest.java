package com.vv.personal.prom.amaterasu.Util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static com.vv.personal.prom.amaterasu.Util.StringUtil.extractStringList;
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
        List<String> result = extractStringList("abcd, ejkeijF34  !   ");
        assertEquals(2, result.size());
        assertEquals("abcd", result.get(0));
        assertEquals("ejkeijF34  !", result.get(1));

        assertTrue(extractStringList("").isEmpty());
        assertTrue(extractStringList(" ").isEmpty());
    }
}