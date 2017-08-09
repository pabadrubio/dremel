/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.parsing.data;

import org.pabad.dremel.parsing.schema.Schema;
import org.pabad.dremel.parsing.data.external.InMemoryNestedData;

public class DremelPaperDataProvider {

    public static InMemoryNestedData getDecoder() {
        return new InMemoryNestedData.Builder()
                .addField("DocId", 10)
                .addField("Links", new InMemoryNestedData.Builder()
                        .addField("Forward", 20)
                        .addField("Forward", 40)
                        .addField("Forward", 60)
                        .build())
                .addField("Name", new InMemoryNestedData.Builder()
                        .addField("Language", new InMemoryNestedData.Builder()
                                .addField("Code", "en-us")
                                .addField("Country", "us")
                                .build())
                        .addField("Language", new InMemoryNestedData.Builder()
                                .addField("Code", "en")
                                .build())
                        .addField("Url", "http://A")
                        .build())
                .addField("Name", new InMemoryNestedData.Builder()
                        .addField("Url", "http://A")
                        .build())
                .addField("Name", new InMemoryNestedData.Builder()
                        .addField("Language", new InMemoryNestedData.Builder()
                                .addField("Code", "en-gb")
                                .addField("Country", "gb")
                                .build())
                        .build())
                .build();
    }

    public static Schema getSchema() {
        return null;
    }
}
