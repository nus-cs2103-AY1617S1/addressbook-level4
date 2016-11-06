//@@author A0133367E
package seedu.agendum.commons.events.logic;

import java.util.Hashtable;

import seedu.agendum.commons.events.BaseEvent;

/**
 * Indicate the alias table in {@link seedu.agendum.logic.commands.CommandLibrary} has changed
 */
public class AliasTableChangedEvent extends BaseEvent {

    public final Hashtable<String, String> aliasTable;
    private String message_;

    public AliasTableChangedEvent(String message, Hashtable<String, String> aliasTable) {
        this.aliasTable = aliasTable;
        this.message_ = message;
    }

    @Override
    public String toString() {
        return message_;
    }
}
