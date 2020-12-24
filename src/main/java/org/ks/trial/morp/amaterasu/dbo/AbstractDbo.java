package org.ks.trial.morp.amaterasu.dbo;

import java.util.Objects;

/**
 * @author Vivek
 * @since 24/12/20
 */
public abstract class AbstractDbo {

    public static Integer generateId(Object... objects) {
        return Math.abs(Objects.hash(objects));
    }
}
