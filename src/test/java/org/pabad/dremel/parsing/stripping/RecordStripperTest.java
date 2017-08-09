/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.parsing.stripping;

import org.junit.Test;
import org.pabad.dremel.parsing.schema.Schema;
import org.pabad.dremel.parsing.data.DremelPaperDataProvider;
import org.pabad.dremel.parsing.data.external.InMemoryNestedData;
import org.pabad.dremel.parsing.data.internal.AtomicField;
import org.pabad.dremel.parsing.stubs.InMemoryColumnarStore;
import org.pabad.dremel.storage.ColumnKey;
import org.pabad.dremel.storage.ColumnScanner;

import static org.junit.Assert.assertTrue;

public class RecordStripperTest {

    @Test
    public void PaperDataStripping() {

        InMemoryNestedData paperDataDecoder = DremelPaperDataProvider.getDecoder();
        Schema paperSchema = DremelPaperDataProvider.getSchema();

        InMemoryColumnarStore store = new InMemoryColumnarStore();
        RecordStripper stripper = new RecordStripper();
        stripper.stripRecords(paperSchema, paperDataDecoder, store);

        // Check the Name.Language.Country column
        ColumnKey countryColumn = new ColumnKey("Name", "Language", "Country");
        ColumnScanner countryColumnScaner = store.getStringColumnScanner(countryColumn);
        AtomicField<String>[] fields = countryColumnScaner.readAll();
        AtomicField<String>[] expectedFields = new AtomicField[]{
                new AtomicField<String>("us", 0, 3),
                new AtomicField<String>(null, 2, 2),
                new AtomicField<String>(null, 1, 1),
                new AtomicField<String>("gb", 1, 3),
                new AtomicField<String>(null, 0, 1)
        };
        assertTrue(expectedFields.equals(fields));
    }

}
