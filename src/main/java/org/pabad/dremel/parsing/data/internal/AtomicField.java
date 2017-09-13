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

    @Override
    public String toString() {
        return String.format("(%s, %d, %d)", value, repetitionLevel, definitionLevel);
    }

    private int definitionLevel;
    private int repetitionLevel;
    private ValueType value;

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null)
            return false;
        if (getClass() != object.getClass())
            return false;
        AtomicField<ValueType> other = (AtomicField<ValueType>) object;
        return (value != null ? value.equals(other.value) : other.value == null) &&
                definitionLevel == other.definitionLevel &&
                repetitionLevel == other.repetitionLevel;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + value.hashCode();
        result = 31 * result + repetitionLevel;
        result = 31 * result + definitionLevel;
        return result;
    }

}
