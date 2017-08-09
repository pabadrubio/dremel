/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.storage;

public class ColumnKey {

    public ColumnKey(String... names) {
        columnSubPaths = names;
    }

    private String[] columnSubPaths;
}
