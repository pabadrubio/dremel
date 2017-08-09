/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.parsing.data.internal;

public class AtomicField<ValueType> {

    public AtomicField(ValueType value, int repetitionLevel, int definitionLevel) {
        this.value = value;
        this.repetitionLevel = repetitionLevel;
        this.definitionLevel = definitionLevel;
    }

    public int getDefinitionLevel() {
        return definitionLevel;
    }

    public int getRepetitionLevel() {
        return repetitionLevel;
    }

    public ValueType getValue() {
        return value;
    }

    private int definitionLevel;
    private int repetitionLevel;
    private ValueType value;

}
