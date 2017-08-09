/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.parsing.schema;

public class StringField extends BaseField {

    public StringField(boolean optional, boolean repeated, String name) {
        super(optional, repeated, name);
    }

    public FieldType getType() {
        return FieldType.STRING;
    }
}
