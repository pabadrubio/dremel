/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.parsing.stubs;

import org.pabad.dremel.parsing.data.internal.AtomicField;
import org.pabad.dremel.storage.ColumnScanner;
import org.pabad.dremel.storage.ColumnWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class InMemoryColumn<ValueType> implements ColumnScanner<ValueType>, ColumnWriter<ValueType> {

    public boolean hasNext() {
        setIterator();
        return iterator.hasNext();
    }

    public AtomicField<ValueType> next() {
        setIterator();
        return iterator.next();
    }

    public List<AtomicField<ValueType>> readAll() {
        return rows;
    }

    public void write(ValueType value, int repetitionLevel, int definitionLevel) {
        rows.add(new AtomicField<ValueType>(value, repetitionLevel, definitionLevel));
    }

    @Override
    public String toString() {
        return rows.toString();
    }

    private void setIterator() {
        if (iterator == null)
            iterator = rows.iterator();
    }

    private List<AtomicField<ValueType>> rows = new ArrayList<>();
    private Iterator<AtomicField<ValueType>> iterator;
}
