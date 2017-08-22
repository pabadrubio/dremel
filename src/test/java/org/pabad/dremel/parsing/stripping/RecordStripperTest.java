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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class RecordStripperTest {

    @Test
    public void PaperDataStripping() {

        InMemoryNestedData paperDataDecoder = DremelPaperDataProvider.getDecoder();
        Schema paperSchema = DremelPaperDataProvider.getSchema();

        InMemoryColumnarStore store = new InMemoryColumnarStore();
        RecordStripper stripper = new RecordStripper();
        paperDataDecoder.reset();
        stripper.stripRecords(paperSchema, paperDataDecoder, store, store);

        // Check the Name.Language.Country column
        ColumnKey countryColumn = new ColumnKey("Document","Name", "Language", "Country");
        ColumnScanner<String> countryColumnScaner = store.getStringColumnScanner(countryColumn);
        List<AtomicField<String>> fields = countryColumnScaner.readAll();
        List<AtomicField<String>> expectedFields = new ArrayList<>();
        expectedFields.add(new AtomicField<String>("us", 0, 3));
        expectedFields.add(new AtomicField<String>(null, 2, 2));
        expectedFields.add(new AtomicField<String>(null, 1, 1));
        expectedFields.add(new AtomicField<String>("gb", 1, 3));
        assertTrue(expectedFields.equals(fields));
    }
}
