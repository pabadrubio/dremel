/**
 * Copyright Pablo Abad 2017
 */
package org.pabad.dremel.parsing.stripping;

public class Level {

    public Level(int repetitionLevel, int definitionLevel) {
        this.repetitionLevel = repetitionLevel;
        this.definitionLevel = definitionLevel;
    }

    public int getDefinitionLevel() {
        return definitionLevel;
    }

    public int getRepetitionLevel() {
        return repetitionLevel;
    }

    private int repetitionLevel;
    private int definitionLevel;

}
