/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.parsing.schema;

public class RecordField extends BaseField {

    public RecordField(boolean optional, boolean repeated, String name, Field[] subFields) {
        super(optional, repeated, name);
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
