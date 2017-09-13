/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.parsing.assembly;

import org.pabad.dremel.parsing.schema.Field;
import org.pabad.dremel.parsing.schema.Group;
import org.pabad.dremel.parsing.schema.Schema;

import java.util.ArrayList;
import java.util.List;

public class FSMBuilder {

    public FieldReader[] constructFSM(Schema schema) {
        List<MutableFieldReader> fields = getFields(schema);
        return constructFSM(fields);
    }

    private FieldReader[] constructFSM(List<MutableFieldReader> fields) {
        for (int i = 0; i < fields.size(); ++i) {
            MutableFieldReader field = fields.get(i);
            int maxLevel = field.getMaximumRepetitionLevel();
            MutableFieldReader barrier = getBarrier(fields, i);
            int barrierLevel = getCommonRepetitionLevel(field, barrier);

            // Calculate transitions to previous elements in the group hierarchy
            for (int preFieldIdx = i; preFieldIdx > 0; --preFieldIdx) {
                MutableFieldReader preField = fields.get(preFieldIdx);
                int commonRepetitionLevel = getCommonRepetitionLevel(field, preField);
                if (commonRepetitionLevel <= barrierLevel)
                    break;
                field.addTransition(commonRepetitionLevel, preField);
            }

            // Copy transitions for groups in the hierarchy without an atomic values as first element
            for (int level = maxLevel-1; level >= barrierLevel+1; --level) {
                if (!field.hasTransition(level))
                    field.addTransition(level, field.getTransitionToMutable(level+1));
            }

            // Finally add transitions to the barrier
            for (int level = 0; level <= barrierLevel; ++level)
                field.addTransition(level, barrier);
        }
        return fields.toArray(new FieldReader[fields.size()]);
    }

    private MutableFieldReader getBarrier(List<MutableFieldReader> fields, int i) {
        if (i == fields.size()-1)
            return new MutableFieldReader(null, 0, new ArrayList<>()); // To signal final FSM state
        else
            return fields.get(i+1);
    }

    private int getCommonRepetitionLevel(MutableFieldReader field1, MutableFieldReader field2) {
        // We first find the common ancestor. To do this we can use a simplified LCA algorithm
        if (field1.equals(field2))
            return field1.getMaximumRepetitionLevel();

        int treeLevel1 = field1.getFieldPath().size()-1;
        int treeLevel2 = field2.getFieldPath().size()-1;
        int level = Math.min(treeLevel1, treeLevel2);
        while (level >= 0 && field1.getFieldPath().get(level) != field2.getFieldPath().get(level))
            --level;

        if (level == -1)
            return 0;
        else
            return field1.getFieldPath().get(level).getMaximumRepetitionLevel();
    }

    private List<MutableFieldReader> getFields(Schema schema) {
        // Adding a fake root makes the algorithm simpler
        MutableFieldReader fakeRoot = new MutableFieldReader(null, 0, new ArrayList<MutableFieldReader>());

        List<MutableFieldReader> atomicFields = new ArrayList<>();
        addFields(fakeRoot, schema.getRootField(), atomicFields);
        return atomicFields;
    }

    private void addFields(MutableFieldReader parent, Field field, List<MutableFieldReader> fields) {
        MutableFieldReader groupData = new MutableFieldReader(field,
                parent.getMaximumRepetitionLevel() + (field.isRepeated() ? 1 : 0),
                appendToPath(parent.getFieldPath(), parent));

        if (field instanceof Group) {
            Group group = (Group)field;
            for (Field subField: group.getSubFields())
                addFields(groupData, subField, fields);
        } else {
            fields.add(groupData); // We only add the atomic fields as they are the only ones taking part in the FSM
        }
    }

    private List<MutableFieldReader> appendToPath(List<MutableFieldReader> fieldPath, MutableFieldReader parent) {
        List<MutableFieldReader> newFieldPath = new ArrayList<>(fieldPath);
        newFieldPath.add(parent);
        return newFieldPath;
    }

}
