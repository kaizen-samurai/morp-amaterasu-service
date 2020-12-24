package com.vv.personal.prom.amaterasu.dbo;

import com.vv.personal.prom.artifactory.proto.Make;

import static com.vv.personal.prom.amaterasu.Util.StringUtil.isValidInput;

/**
 * @author Vivek
 * @since 24/12/20
 */
public class MakeDbo extends AbstractDbo {

    public static Integer generateMakeId(String makeName) {
        return generateId(makeName);
    }

    public static Make generateMakeProto(Integer makeId, String makeName) {
        return Make.newBuilder()
                .setMakeId(makeId)
                .setMakeName(makeName)
                .build();
    }

    public static boolean verifyMakeDetails(String make) { //maintaining convention
        return isValidMake(make);
    }

    public static boolean isValidMake(String make) {
        return isValidInput(make);
    }
}
