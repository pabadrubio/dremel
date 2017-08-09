/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.storage;

public interface ColumnarStoreScanner {

    ColumnScanner<String> getStringColumnScanner(ColumnKey column);
    ColumnScanner<Integer> getIntegerColumnScanner(ColumnKey column);
    ColumnScanner<Boolean> getBooleanColumnScanner(ColumnKey column);

}
