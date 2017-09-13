/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.parsing.assembly;

import org.pabad.dremel.parsing.schema.Field;

public interface FieldReader {
    Field getField();
    FieldReader getTransition(int i);
}
