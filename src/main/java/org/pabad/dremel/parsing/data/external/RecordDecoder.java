/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.parsing.data.external;

/**
 * Allows to read a structured nested data type one field after another
 */
public interface RecordDecoder {
    boolean hasNextField();
    Field getNextField();
}
