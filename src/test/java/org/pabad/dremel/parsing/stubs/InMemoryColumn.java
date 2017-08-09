/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.parsing.stubs;

import org.pabad.dremel.parsing.data.internal.AtomicField;
import org.pabad.dremel.storage.ColumnScanner;
import org.pabad.dremel.storage.ColumnWriter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InMemoryColumn<ValueType> implements ColumnScanner<ValueType>, ColumnWriter<ValueType> {

    public boolean hasNext() {
        setIterator();
        return iterator.hasNext();
    }

    public ValueType next() {
        setIterator();
        return iterator.next();
    }

    public AtomicField<ValueType>[] readAll() {
        return null;
    }

    public void write(ValueType value, int repetitionLevel, int definitionLevel) {
        rows.add(value);
        repetitionLevels.add(repetitionLevel);
        definitionLevels.add(definitionLevel);
    }

    private void setIterator() {
        if (iterator == null)
            iterator = rows.iterator();
    }

    private List<ValueType> rows = new ArrayList<ValueType>();
    private List<Integer> definitionLevels = new ArrayList<Integer>();
    private List<Integer> repetitionLevels = new ArrayList<Integer>();
    private Iterator<ValueType> iterator;
}
