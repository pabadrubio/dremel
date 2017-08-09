/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.parsing.data;

import org.junit.Test;
import org.pabad.dremel.parsing.data.external.InMemoryNestedData;

import static org.junit.Assert.*;

public class InMemoryNestedDataTest {

    @Test
    public void PaperDecoding() {
        InMemoryNestedData nestedData = DremelPaperDataProvider.getDecoder();

        String expectedValue =
                "DocId: 10\n" +
                "Links\n" +
                "  Forward: 20\n" +
                "  Forward: 40\n" +
                "  Forward: 60\n" +
                "Name\n" +
                "  Language\n" +
                "    Code: en-us\n" +
                "    Country: us\n" +
                "  Language\n" +
                "    Code: en\n" +
                "  Url: http://A\n" +
                "Name\n" +
                "  Url: http://A\n" +
                "Name\n" +
                "  Language\n" +
                "    Code: en-gb\n" +
                "    Country: gb\n";

        assertTrue(nestedData.toString().equals(expectedValue));
    }

}
