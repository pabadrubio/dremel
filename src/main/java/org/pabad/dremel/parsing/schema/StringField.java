/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.parsing.schema;

public class StringField extends AtomicField {

    public StringField(FieldCardinality cardinality, String name) {
        super(cardinality, name);
    }

    public FieldType getType() {
        return FieldType.STRING;
    }
}
