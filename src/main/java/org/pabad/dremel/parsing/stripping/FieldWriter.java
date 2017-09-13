/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.parsing.stripping;

import org.pabad.dremel.parsing.data.external.AtomicField;
import org.pabad.dremel.parsing.schema.Field;
import org.pabad.dremel.parsing.schema.FieldType;
import org.pabad.dremel.storage.ColumnKey;
import org.pabad.dremel.storage.ColumnWriter;
import org.pabad.dremel.storage.ColumnarStoreSchemaSetter;
import org.pabad.dremel.storage.ColumnarStoreWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FieldWriter {

    public FieldWriter(
            Field field,
            FieldWriter parent,
            ColumnarStoreSchemaSetter storeSchemaSetter,
            ColumnarStoreWriter columnStoreWriter) {
        this.parent = parent;
        if (parent == null) {
            this.depth = depth;
            this.columnKey = new ColumnKey(field.getName());
        }
        else {
            this.depth = parent.getDepth() + 1;
            this.columnKey = parent.getColumnKey().subColumn(field.getName());
        }
        this.atomicField = field.getType() != FieldType.RECORD;
        this.required = field.isRequired();

        if (field.getType() == FieldType.INTEGER) {
            storeSchemaSetter.addIntColumn(columnKey);
            this.writer = getIntegerWriter(columnStoreWriter, columnKey);
        }
        else if (field.getType() == FieldType.STRING) {
            storeSchemaSetter.addStringColumn(columnKey);
            this.writer = getStringWriter(columnStoreWriter, columnKey);
        }
    }

    public void addChild(String key, FieldWriter child) {
        childs.put(key, child);
    }

    public void addLevels(int repetitionLevel, int definitionLevel) {
        flush(false);
        levels.add(new Level(repetitionLevel, definitionLevel));
    }

    public FieldWriter getChildFieldWriter(String fieldId) {
        return childs.get(fieldId);
    }

    public void write(AtomicField value, int chRepetitionLevel, int definitionLevel) {
        flush(false);
        writer.writeValue(value, chRepetitionLevel, definitionLevel);
    }

    public void recursiveFlush() {
        flush(true);
        for (FieldWriter f : childs.values())
            f.recursiveFlush();
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
        if (parent != null) {
            if (isAtomicField())
                atomicFieldFlush(flushLastElement);
            else
                recordFieldFlush(flushLastElement);
        }
    }

    private void atomicFieldFlush(boolean flushLastElement) {
        List<Level> parentLevels = parent.levels;
        int parentLevelsSize = parentLevels.size();
        int lastLevel = parentLevelsSize - 2;
        if (flushLastElement)
            lastLevel += 1;
        for(int l = lastParentLevel + 1; l <= lastLevel; ++l) {
            Level level = parentLevels.get(l);
            writer.writeNull(level.getRepetitionLevel(), level.getDefinitionLevel());
        }
        lastParentLevel = lastLevel + 1;
    }

    private void recordFieldFlush(boolean flushLastElement) {
        List<Level> parentLevels = parent.levels;
        int parentLevelsSize = parentLevels.size();
        int lastLevel = parentLevelsSize - 2;
        if (flushLastElement)
            lastLevel += 1;
        for(int l = lastParentLevel + 1; l <= lastLevel; ++l) {
            Level level = parentLevels.get(l);
            levels.add(level);
        }
        lastParentLevel = lastLevel + 1;
    }

    private ColumnKey getColumnKey() {
        return columnKey;
    }

    private interface Writer {
        void writeNull(int repetitionLevel, int definitionLevel);
        void writeValue(AtomicField value, int repetitionLevel, int definitionLevel);
    }

    private Writer getIntegerWriter(final ColumnarStoreWriter columnStoreWriter, final ColumnKey columnKey) {
        return new Writer() {

            ColumnWriter<Integer> intWriter = columnStoreWriter.getIntegerColumnWriter(columnKey);

            public void writeNull(int repetitionLevel, int definitionLevel)
            {
                intWriter.write(null, repetitionLevel, definitionLevel);
            }

            public void writeValue(AtomicField value, int repetitionLevel, int definitionLevel)
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

            public void writeValue(AtomicField value, int repetitionLevel, int definitionLevel)
            {
                String stringValue = value.getValue();
                stringWriter.write(stringValue, repetitionLevel, definitionLevel);
            }
        };
    }

    private List<Level> levels = new ArrayList<Level>();
    private FieldWriter parent;
    private Map<String, FieldWriter> childs = new HashMap<String, FieldWriter>();
    private int depth;
    private boolean atomicField;
    private boolean required;
    private int lastParentLevel = -1;
    private ColumnKey columnKey;

    Writer writer;
}
