/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.parsing.schema;

public class Group extends AtomicField {

    public Group(FieldCardinality cardinality, String name, Field[] subFields) {
        super(cardinality, name);
        this.subFields = subFields;
    }

    public FieldType getType() {
        return FieldType.RECORD;
    }

    public Field[] getSubFields() {
        return subFields;
    }

    private Field[] subFields;
}
