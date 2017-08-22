/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.storage;

import org.pabad.dremel.parsing.data.internal.AtomicField;

import java.util.List;

public interface ColumnScanner<ValueType> {

    boolean hasNext();

    AtomicField<ValueType> next();

    List<AtomicField<ValueType>> readAll();

}
