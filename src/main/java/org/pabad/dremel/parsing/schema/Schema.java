/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.parsing.schema;

/**
 * A hierarchical data definition schema
 */
public class Schema {

    public Schema(RecordField rootField) {
        this.rootField = rootField;
    }

    public RecordField getRootField() {
        return rootField;
    }

    private RecordField rootField;
}
