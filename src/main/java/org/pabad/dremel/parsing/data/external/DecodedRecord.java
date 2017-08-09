/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.parsing.data.external;

/**
 * A record decoded by the record decoder
 */
public class DecodedRecord implements Field {

    public DecodedRecord(String name, RecordDecoder decoder) {
        this.name = name;
        this.decoder = decoder;
    }

    public boolean isRecord() {
        return true;
    }

    public String getName() {
        return name;
    }

    public RecordDecoder getRecordDecoder() {
        return decoder;
    }

    private String name;
    private RecordDecoder decoder;
}
