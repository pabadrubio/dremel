/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.storage;

import org.pabad.dremel.parsing.data.internal.AtomicField;

public interface ColumnScanner<ValueType> {

    boolean hasNext();

    ValueType next();

    AtomicField<ValueType>[] readAll();

}
