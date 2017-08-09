/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.parsing.schema;

/**
 * A field in the data schema
 */
public interface Field {

    FieldType getType();

    boolean isOptional();

    boolean isRepeated();

    String getName();
}
