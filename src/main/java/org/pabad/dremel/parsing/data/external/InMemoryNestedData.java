/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.parsing.data.external;

import java.util.ArrayList;
import java.util.List;

/**
 * A RecordDecoder with stores all data in memory
 */
public class InMemoryNestedData implements RecordDecoder {

    public boolean hasNextField() {
        return index < fieldValues.size();
    }

    public Field getNextField() {
        FieldData fieldData = fieldValues.get(index);
        Field field;
        if (fieldData.value instanceof InMemoryNestedData)
            field = new Group(fieldData.name, (InMemoryNestedData)fieldData.value);
        else
            field = new AtomicField(fieldData.name, fieldData.value);
        index++;
        return field;
    }

    public void reset() {
        for (FieldData field: fieldValues) {
            if (field.value instanceof InMemoryNestedData)
                ((InMemoryNestedData)(field.value)).reset();
        }
        index = 0;
    }

    @Override
    public String toString() {
        return toString("  ", "\n");
    }

    public String toString(String tabulator, String newLine) {
        StringBuilder builder = new StringBuilder();
        buildString(tabulator, newLine, "", builder);
        return builder.toString();
    }

    private void buildString(String tabulator, String newLine, String tabulatorLevel, StringBuilder builder) {
        int lastIndex = index;
        index = 0;
        while(hasNextField()) {
            Field field = getNextField();
            builder.append(tabulatorLevel);
            builder.append(field.getName());
            if (field.isGroup()) {
                builder.append(newLine);
                InMemoryNestedData decoder = (InMemoryNestedData) ((Group)field).getRecordDecoder();
                decoder.buildString(tabulator, newLine, tabulatorLevel + tabulator, builder);
            }
            else {
                builder.append(": ");
                AtomicField valueField = (AtomicField)field;
                builder.append(valueField.getValue().toString());
                builder.append(newLine);
            }
        }
        index = lastIndex;
    }

    private static class FieldData {
        public FieldData(String name, Object value) {
            this.name = name;
            this.value = value;
        }
        public String name;
        public Object value;
    }

    public static class Builder {

        public <ValueType> InMemoryNestedData.Builder addField(String name, ValueType value) {
            fieldValues.add(new InMemoryNestedData.FieldData(name, value));
            return this;
        }

        public InMemoryNestedData.Builder addGroup(String name, InMemoryNestedData value) {
            fieldValues.add(new InMemoryNestedData.FieldData(name, value));
            return this;
        }

        public InMemoryNestedData build() {
            return new InMemoryNestedData(fieldValues);
        }

        private List<InMemoryNestedData.FieldData> fieldValues = new ArrayList<InMemoryNestedData.FieldData>();
    }

    private InMemoryNestedData(List<FieldData> fieldValues) {
        this.fieldValues = fieldValues;
    }

    private List<FieldData> fieldValues;
    private int index = 0;
}
