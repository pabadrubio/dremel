/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.parsing.assembly;

import org.pabad.dremel.parsing.schema.Field;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MutableFieldReader implements FieldReader {

    public MutableFieldReader(Field field, int maximumRepetitionLevel, List<MutableFieldReader> fieldPath) {
        this.field = field;
        this.maximumRepetitionLevel = maximumRepetitionLevel;
        this.fieldPath = fieldPath;
    }

    @Override
    public Field getField() {
        return field;
    }

    @Override public FieldReader getTransition(int i) {
        return  getTransitionToMutable(i);
    }

    @Override public String toString() {
        if (field != null)
            return field.getName();
        else
            return "NO FIELD";
    }

    public int getMaximumRepetitionLevel() {
        return maximumRepetitionLevel;
    }

    public List<MutableFieldReader> getFieldPath() {
        return fieldPath;
    }

    public void addTransition(int level, MutableFieldReader field) {
        transitions.put(level, field);
    }

    public boolean hasTransition(int level) {
        return transitions.containsKey(level);
    }

    public MutableFieldReader getTransitionToMutable(int i) {
        return transitions.get(i);
    }

    private Field field;
    private int maximumRepetitionLevel; // Repeated groups up to here
    private List<MutableFieldReader> fieldPath; // The path from the root to the parent of the field (included)

    // This could be optimized to an array, as every level up to the maximum repetition level has a transition
    private Map<Integer, MutableFieldReader> transitions = new HashMap<>();

}