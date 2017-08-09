/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.parsing.stubs;

import org.pabad.dremel.storage.*;

import java.util.HashMap;
import java.util.Map;

public class InMemoryColumnarStore implements ColumnarStoreSchemaSetter, ColumnarStoreWriter, ColumnarStoreScanner {


    public void addStringColumn(ColumnKey columnKey) {

    }

    public void addIntColumn(ColumnKey columnKey) {

    }

    public void addBooleanColumn(ColumnKey columnKey) {

    }

    public ColumnWriter<String> getStringColumnWriter(ColumnKey column) {
        return null;
    }

    public ColumnWriter<Integer> getIntegerColumnWriter(ColumnKey column) {
        return null;
    }

    public ColumnWriter<Boolean> getBooleanColumnWriter(ColumnKey column) {
        return null;
    }

    public ColumnScanner<String> getStringColumnScanner(ColumnKey column) {
        return null;
    }

    public ColumnScanner<Integer> getIntegerColumnScanner(ColumnKey column) {
        return null;
    }

    public ColumnScanner<Boolean> getBooleanColumnScanner(ColumnKey column) {
        return null;
    }


    private Map<ColumnKey, InMemoryColumn> columns = new HashMap<ColumnKey, InMemoryColumn>();
}
