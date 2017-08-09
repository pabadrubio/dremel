/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.parsing.schema;

public class IntegerField extends BaseField {

    public IntegerField(boolean optional, boolean repeated, String name) {
        super(optional, repeated, name);
    }

    public FieldType getType() {
        return FieldType.RECORD;
    }
}
