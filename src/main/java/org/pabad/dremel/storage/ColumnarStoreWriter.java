/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.storage;

public interface ColumnarStoreWriter {

    ColumnWriter<String> getStringColumnWriter(ColumnKey column);
    ColumnWriter<Integer> getIntegerColumnWriter(ColumnKey column);
    ColumnWriter<Boolean> getBooleanColumnWriter(ColumnKey column);

}
