/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.parsing.schema;

/**
 * A hierarchical data definition schema
 */
public class Schema {

    public Schema(Group rootField) {
        this.rootField = rootField;
    }

    public Group getRootField() {
        return rootField;
    }

    private Group rootField;
}
