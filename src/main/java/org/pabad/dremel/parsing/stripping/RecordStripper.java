/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.parsing.stripping;

import org.pabad.dremel.parsing.schema.Field;
import org.pabad.dremel.parsing.data.external.AtomicField;
import org.pabad.dremel.parsing.schema.FieldType;
import org.pabad.dremel.parsing.schema.Group;
import org.pabad.dremel.parsing.schema.Schema;
import org.pabad.dremel.parsing.data.external.RecordDecoder;
import org.pabad.dremel.storage.ColumnarStoreSchemaSetter;
import org.pabad.dremel.storage.ColumnarStoreWriter;

import java.util.HashSet;
import java.util.Set;

public class RecordStripper {

    public void stripRecords(
            Schema schema,
            RecordDecoder decoder,
            ColumnarStoreSchemaSetter storeSchemaSetter,
            ColumnarStoreWriter storeWriter) {
        FieldWriter rootWriter = createFieldWriter(schema.getRootField(), null, storeSchemaSetter, storeWriter);
        dissectRecord(decoder, rootWriter, 0, 0);
        rootWriter.recursiveFlush();
    }

    private FieldWriter createFieldWriter(
            Field field,
            FieldWriter parent,
            ColumnarStoreSchemaSetter storeSchemaSetter,
            ColumnarStoreWriter storeWriter) {
        boolean isAtomic = field.getType() != FieldType.RECORD;
        FieldWriter fieldWriter = new FieldWriter(field, parent, storeSchemaSetter, storeWriter);
        if (!isAtomic) {
            Group recordField = (Group) field;
            Field[] subFields = recordField.getSubFields();
            for (Field f : subFields) {
                FieldWriter subFieldWriter = createFieldWriter(f, fieldWriter, storeSchemaSetter, storeWriter);
                fieldWriter.addChild(f.getName(), subFieldWriter);
            }
        }
        return fieldWriter;
    }

    private void dissectRecord(RecordDecoder decoder, FieldWriter writer, int repetitionLevel, int definitionLevel) {
        Set<String> seenFields = new HashSet<String>();
        if (decoder.hasNextField())
            writer.addLevels(repetitionLevel, definitionLevel);
        while (decoder.hasNextField()) {
            org.pabad.dremel.parsing.data.external.Field field = decoder.getNextField();
            String fieldId = field.getName();
            FieldWriter chWriter = writer.getChildFieldWriter(fieldId);
            int chRepetitionLevel = repetitionLevel;
            if (seenFields.contains(fieldId))
                chRepetitionLevel = chWriter.getDepth();
            else
                seenFields.add(fieldId);

            int chDefinitionLevel = definitionLevel;
            if (!chWriter.isRequired())
                chDefinitionLevel += 1;

            if (chWriter.isAtomicField()) {
                AtomicField valueField = (AtomicField)field;
                chWriter.write(valueField, chRepetitionLevel, chDefinitionLevel);
            } else {
                org.pabad.dremel.parsing.data.external.Group rField = (org.pabad.dremel.parsing.data.external.Group)field;
                dissectRecord(rField.getRecordDecoder(), chWriter, chRepetitionLevel, chDefinitionLevel);
            }
        }
    }
}
