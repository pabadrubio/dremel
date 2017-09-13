/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.parsing.data.external;

/**
 * A field in a record with an associated value
 */
public class AtomicField implements Field {

    public AtomicField(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public boolean isGroup() {
        return false;
    }

    public String getName() {
        return name;
    }

    public <ValueType> ValueType getValue() {
        return (ValueType)value;
    }

    private String name;
    private Object value;
}
