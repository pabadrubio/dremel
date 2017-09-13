/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.parsing.data.external;

/**
 * A record decoded by the record decoder
 */
public class Group implements Field {

    public Group(String name, RecordDecoder decoder) {
        this.name = name;
        this.decoder = decoder;
    }

    public boolean isGroup() {
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
