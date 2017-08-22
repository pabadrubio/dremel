/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.parsing.stubs;

import org.pabad.dremel.storage.*;

import java.util.HashMap;
import java.util.Map;

public class InMemoryColumnarStore implements ColumnarStoreSchemaSetter, ColumnarStoreWriter, ColumnarStoreScanner {


    public void addStringColumn(ColumnKey columnKey) {
        stringColumns.put(columnKey, new InMemoryColumn<String>());
    }

    public void addIntColumn(ColumnKey columnKey) {
        integerColumns.put(columnKey, new InMemoryColumn<Integer>());
    }

    public void addBooleanColumn(ColumnKey columnKey) {
        booleanColumns.put(columnKey, new InMemoryColumn<Boolean>());
    }

    public ColumnWriter<String> getStringColumnWriter(ColumnKey column) {
        return stringColumns.get(column);
    }

    public ColumnWriter<Integer> getIntegerColumnWriter(ColumnKey column) {
        return integerColumns.get(column);
    }

    public ColumnWriter<Boolean> getBooleanColumnWriter(ColumnKey column) {
        return booleanColumns.get(column);
    }

    public ColumnScanner<String> getStringColumnScanner(ColumnKey column) {
        return stringColumns.get(column);
    }

    public ColumnScanner<Integer> getIntegerColumnScanner(ColumnKey column) {
        return integerColumns.get(column);
    }

    public ColumnScanner<Boolean> getBooleanColumnScanner(ColumnKey column) {
        return booleanColumns.get(column);
    }


    private Map<ColumnKey, InMemoryColumn<Integer>> integerColumns = new HashMap<ColumnKey, InMemoryColumn<Integer>>();
    private Map<ColumnKey, InMemoryColumn<String>> stringColumns = new HashMap<ColumnKey, InMemoryColumn<String>>();
    private Map<ColumnKey, InMemoryColumn<Boolean>> booleanColumns = new HashMap<ColumnKey, InMemoryColumn<Boolean>>();

}
