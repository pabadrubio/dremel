/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.parsing.schema;

/**
 * A field which contains data
 */
public abstract class BaseField implements Field {

    public BaseField(boolean optional, boolean repeated, String name) {
        this.optional = optional;
        this.repeated = repeated;
        this.name = name;
    }

    public boolean isOptional() {
        return false;
    }

    public boolean isRepeated() {
        return false;
    }

    public String getName() {
        return null;
    }

    private boolean optional;
    private boolean repeated;
    private String name;
}
