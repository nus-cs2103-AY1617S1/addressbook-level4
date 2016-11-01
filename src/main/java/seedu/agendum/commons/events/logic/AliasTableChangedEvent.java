//@@author A0133367E
package seedu.agendum.commons.events.logic;

import seedu.agendum.commons.events.BaseEvent;

import java.util.*;

/**
 * Indicate the alias table in Logic's command library has changed
 */
public class AliasTableChangedEvent extends BaseEvent {

    public final String aliasedKeyChanged;
    public final Hashtable<String, String> aliasTable;

    public AliasTableChangedEvent(String aliasedKeyChanged, Hashtable<String, String> aliasTable) {
        this.aliasedKeyChanged = aliasedKeyChanged;
        this.aliasTable = aliasTable;
    }

    @Override
    public String toString() {
        return aliasedKeyChanged;
    }
}
