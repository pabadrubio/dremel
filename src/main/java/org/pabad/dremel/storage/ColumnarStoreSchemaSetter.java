/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.storage;

public interface ColumnarStoreSchemaSetter {

    void addStringColumn(ColumnKey columnKey);
    void addIntColumn(ColumnKey columnKey);
    void addBooleanColumn(ColumnKey columnKey);

}
