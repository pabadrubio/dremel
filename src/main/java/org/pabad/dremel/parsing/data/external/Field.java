/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.parsing.data.external;

/**
 * A field in a record, which can be a nested group or a field with a basic value
 */
public interface Field {
    boolean isGroup();
    String getName();
}
