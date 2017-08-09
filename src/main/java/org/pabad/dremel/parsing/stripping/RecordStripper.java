/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.parsing.stripping;

import org.pabad.dremel.parsing.data.external.DecodedRecord;
import org.pabad.dremel.parsing.data.external.Field;
import org.pabad.dremel.parsing.data.external.ValueField;
import org.pabad.dremel.parsing.schema.Schema;
import org.pabad.dremel.parsing.data.external.RecordDecoder;
import org.pabad.dremel.storage.ColumnarStoreWriter;

import java.util.HashSet;
import java.util.Set;

public class RecordStripper<NameType> {

    /**
     * Implements the column strip algorithm
     * @param schema
     * @param data
     * @param writer
     */
    public void stripRecords(Schema schema, RecordDecoder decoder, ColumnarStoreWriter writer) {
        FieldWriter rootWriter = createFieldWritersFromSchema(schema);
        dissectRecord(decoder, rootWriter, 0, 0);
    }

    /**
     * Creates the field writers from the schema and returns the root writer
     * @param schema
     * @return
     */
    private FieldWriter createFieldWritersFromSchema(Schema schema) {
        return null;
    }

    private void dissectRecord(RecordDecoder decoder, FieldWriter writer, int repetitionLevel, int definitionLevel) {
        writer.addLevels(repetitionLevel, definitionLevel);
        Set<String> seenFields = new HashSet<String>();
        while (decoder.hasNextField()) {
            Field field = decoder.getNextField();
            String fieldId = field.getName();
            FieldWriter chWriter = writer.getChildFieldWriter(fieldId);
            int chRepetitionLevel = repetitionLevel;
            if (seenFields.contains(fieldId))
                repetitionLevel = writer.getDepth();
            else
                seenFields.add(fieldId);

            int chDefinitionLevel = definitionLevel;
            if (!chWriter.isRequired())
                chDefinitionLevel += 1;

            if (chWriter.isAtomicField()) {
                ValueField valueField = (ValueField)field;
                chWriter.write(valueField, chRepetitionLevel, chDefinitionLevel);
            } else {
                DecodedRecord rField = (DecodedRecord)field;
                dissectRecord(rField.getRecordDecoder(), chWriter, chRepetitionLevel, chDefinitionLevel);
            }
        }
    }
}
