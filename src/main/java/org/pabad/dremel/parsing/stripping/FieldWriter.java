/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.parsing.stripping;

import org.pabad.dremel.parsing.data.external.ValueField;
import org.pabad.dremel.storage.ColumnKey;
import org.pabad.dremel.storage.ColumnWriter;
import org.pabad.dremel.storage.ColumnarStoreWriter;

import java.util.List;
import java.util.Map;

public class FieldWriter {

    public FieldWriter(List<Level> levels, FieldWriter parent, Map<String, FieldWriter> childs, int depth, boolean atomicField, boolean required, int lastParentLevel) {
        this.levels = levels;
        this.parent = parent;
        this.childs = childs;
        this.depth = depth;
        this.atomicField = atomicField;
        this.required = required;
        this.lastParentLevel = lastParentLevel;
    }

    public void addLevels(int repetitionLevel, int definitionLevel) {
        levels.add(new Level(repetitionLevel, definitionLevel));
    }

    public FieldWriter getChildFieldWriter(String fieldId) {
        return childs.get(fieldId);
    }

    public void write(ValueField value, int chRepetitionLevel, int definitionLevel) {
        flush(false);
        writer.writeValue(value, chRepetitionLevel, definitionLevel);
    }

    public void flush() {
        flush(true);
    }

    public int getDepth() {
        return depth;
    }

    public boolean isAtomicField() {
        return atomicField;
    }

    public boolean isRequired() {
        return required;
    }

    private void flush(boolean flushLastElement) {
        List<Level> parentLevels = parent.levels;
        int parentLevelsSize = parentLevels.size();
        int lastLevel = parentLevelsSize - 1;
        if (flushLastElement)
            lastLevel += 1;
        for(int l = lastParentLevel + 1; l <= lastLevel; ++l) {
            Level level = parentLevels.get(l);
            writer.writeValue(null, level.getRepetitionLevel(), level.getDefinitionLevel());
        }
        lastParentLevel = lastLevel;
    }

    private interface Writer {
        void writeNull(int repetitionLevel, int definitionLevel);
        void writeValue(ValueField value, int repetitionLevel, int definitionLevel);
    }

    private Writer getIntegerWriter(final ColumnarStoreWriter columnStoreWriter, final ColumnKey columnKey) {
        return new Writer() {

            ColumnWriter<Integer> intWriter = columnStoreWriter.getIntegerColumnWriter(columnKey);

            public void writeNull(int repetitionLevel, int definitionLevel)
            {
                intWriter.write(null, repetitionLevel, definitionLevel);
            }

            public void writeValue(ValueField value, int repetitionLevel, int definitionLevel)
            {
                Integer integerValue = value.getValue();
                intWriter.write(integerValue, repetitionLevel, definitionLevel);
            }
        };
    }

    private Writer getStringWriter(final ColumnarStoreWriter columnStoreWriter, final ColumnKey columnKey) {
        return new Writer() {

            ColumnWriter<String> stringWriter = columnStoreWriter.getStringColumnWriter(columnKey);

            public void writeNull(int repetitionLevel, int definitionLevel)
            {
                stringWriter.write(null, repetitionLevel, definitionLevel);
            }

            public void writeValue(ValueField value, int repetitionLevel, int definitionLevel)
            {
                String stringValue = value.getValue();
                stringWriter.write(stringValue, repetitionLevel, definitionLevel);
            }
        };
    }

    private List<Level> levels;
    private FieldWriter parent;
    private Map<String, FieldWriter> childs;
    private int depth;
    private boolean atomicField;
    private boolean required;
    private int lastParentLevel = -1;

    Writer writer;
}
