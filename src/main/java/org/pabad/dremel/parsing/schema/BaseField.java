/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.parsing.schema;

/**
 * A field which contains data
 */
public abstract class BaseField implements Field {

    public BaseField(FieldCardinality cardinality, String name) {
        this.cardinality = cardinality;
        this.name = name;
    }

    public FieldCardinality getCardinality() {
        return cardinality;
    }

    public boolean isRequired() {
        return cardinality == FieldCardinality.REQUIRED || cardinality == FieldCardinality.MESSAGE_ROOT;
    }

    public boolean isOptional() {
        return cardinality == FieldCardinality.OPTIONAL;
    }

    public boolean isRepeated() {
        return cardinality == FieldCardinality.REPEATED;
    }

    public String getName() {
        return name;
    }

    private FieldCardinality cardinality;
    private String name;
}
