/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.parsing.assembly;

import org.junit.Test;
import org.pabad.dremel.parsing.data.DremelPaperDataProvider;
import org.pabad.dremel.parsing.schema.Schema;

import static org.junit.Assert.assertEquals;

public class FSMBuilderTest {

    @Test
    public void PaperDataStripping() {
        Schema paperSchema = DremelPaperDataProvider.getSchema();
        FSMBuilder builder = new FSMBuilder();
        FieldReader[] fieldReaders = builder.constructFSM(paperSchema);

        assertEquals("DocId", fieldReaders[0].getField().getName());
        assertEquals("Backward", fieldReaders[0].getTransition(0).getField().getName());

        assertEquals("Backward", fieldReaders[1].getField().getName());
        assertEquals("Forward", fieldReaders[1].getTransition(0).getField().getName());
        assertEquals("Backward", fieldReaders[1].getTransition(1).getField().getName());

        assertEquals("Forward", fieldReaders[2].getField().getName());
        assertEquals("Code", fieldReaders[2].getTransition(0).getField().getName());
        assertEquals("Forward", fieldReaders[2].getTransition(1).getField().getName());

        assertEquals("Code", fieldReaders[3].getField().getName());
        assertEquals("Country", fieldReaders[3].getTransition(0).getField().getName());
        assertEquals("Country", fieldReaders[3].getTransition(1).getField().getName());
        assertEquals("Country", fieldReaders[3].getTransition(2).getField().getName());

        assertEquals("Country", fieldReaders[4].getField().getName());
        assertEquals("Url", fieldReaders[4].getTransition(0).getField().getName());
        assertEquals("Url", fieldReaders[4].getTransition(1).getField().getName());
        assertEquals("Code", fieldReaders[4].getTransition(2).getField().getName());

        assertEquals("Url", fieldReaders[5].getField().getName());
        assertEquals(null, fieldReaders[5].getTransition(0).getField());

    }

}
