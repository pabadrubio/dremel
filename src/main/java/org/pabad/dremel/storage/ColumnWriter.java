/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.storage;

public interface ColumnWriter<ValueType> {

    void write(ValueType value, int repetitionLevel, int definitionLevel);

}
