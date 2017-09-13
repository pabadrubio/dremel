/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.parsing.schema;

public class IntegerField extends AtomicField {

    public IntegerField(FieldCardinality cardinality, String name) {
        super(cardinality, name);
    }

    public FieldType getType() {
        return FieldType.INTEGER;
    }
}
