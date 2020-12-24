package com.vv.personal.prom.amaterasu.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.vv.personal.prom.amaterasu.constants.Constants.COMMA_STR;

/**
 * @author Vivek
 * @since 23/12/20
 */
public class StringUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtil.class);

    public static List<String> extractStringList(String data) {
        return extractStringList(data, COMMA_STR);
    }

    public static List<String> extractStringList(String data, String splitter) {
        try {
            return Arrays.stream(data.split(splitter))
                    .map(String::strip)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOGGER.error("Failed to convert to string list for {}. ", data, e);
        }
        return new ArrayList<>();
    }

    public static boolean isValidInput(String name) {
        return !name.strip().isEmpty();
    }

}