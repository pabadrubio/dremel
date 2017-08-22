/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.storage;

import java.util.Arrays;

public class ColumnKey {

    public ColumnKey(String... names) {
        columnSubPaths = names;
    }

    private String[] columnSubPaths;

    public ColumnKey subColumn(String name) {
        String[] newSubPaths = Arrays.copyOf(columnSubPaths, columnSubPaths.length + 1);
        newSubPaths[columnSubPaths.length] = name;
        return new ColumnKey(newSubPaths);
    }

    @Override
    public String toString() {
        return Arrays.toString(columnSubPaths);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null)
            return false;
        if (getClass() != object.getClass())
            return false;
        ColumnKey other = (ColumnKey) object;
        return Arrays.equals(columnSubPaths, other.columnSubPaths);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(columnSubPaths);
    }

}
