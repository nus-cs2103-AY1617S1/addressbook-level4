package seedu.lifekeeper.commons.events.model;

import java.io.File;

import seedu.lifekeeper.commons.events.BaseEvent;
import seedu.lifekeeper.logic.Logic;

/**
 * Indicates the storage to read from a new data file.
 */
public class LoadLifekeeperEvent extends BaseEvent{
    
    public final File openFile;
    public final Logic logic;
    
    public LoadLifekeeperEvent(File openFile, Logic logic) {
        this.openFile = openFile;
        this.logic = logic;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
