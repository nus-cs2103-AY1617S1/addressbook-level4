//@@author A0133367E

package seedu.agendum.logic.commands;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.reflections.Reflections;
import seedu.agendum.commons.core.EventsCenter;
import seedu.agendum.commons.core.LogsCenter;
import seedu.agendum.commons.events.logic.AliasTableChangedEvent;

/**
 * Manages and stores the various Agendum's reserved command keywords and their aliases
 */
public class CommandLibrary {

    private static final Logger logger = LogsCenter.getLogger(CommandLibrary.class);
    private List<String> allCommandWords = new ArrayList<String>();

    // The keys of the hash table are user-defined aliases
    // The values of the has table are Agendum's reserved command keywords
    private Hashtable<String, String> aliasTable = new Hashtable<String, String>();

    private static CommandLibrary commandLibrary = new CommandLibrary();

    //@@author A0003878Y
    private CommandLibrary() {
        allCommandWords = new Reflections("seedu.agendum").getSubTypesOf(Command.class)
                .stream()
                .map(s -> {
                    try {
                        return s.getMethod("getName").invoke(null).toString();
                    } catch (NullPointerException e) {
                        return null;
                    } catch (Exception e) {
                        logger.severe("Java reflection for Command class failed");
                        throw new RuntimeException();
                    }
                })
                .filter(p -> p != null) // remove nulls
                .collect(Collectors.toList());
    }

    public static CommandLibrary getInstance() {
        return commandLibrary;
    }

    //@@author A0133367E
    /**
     * Replace the current commandLibrary's aliasTable with the aliasTable provided
     */
    public void loadAliasTable(Hashtable<String, String> aliasTable) {
        this.aliasTable = aliasTable;
    }

    /**
     * Returns true if key is already an alias for a command keyword, false otherwise.
     */
    public boolean isExistingAliasKey(String key) {
        assert key != null;
        assert key.equals(key.toLowerCase());

        return aliasTable.containsKey(key);
    }

    /**
     * Precondition: key is an existing alias.
     * Returns the reserved command keyword that is aliased by key
     */
    public String getAliasedValue(String key) {
        assert isExistingAliasKey(key);

        return aliasTable.get(key);
    }

    /**
     * Returns true if value is a reserved command keyword, false otherwise
     */
    public boolean isReservedCommandKeyword(String value) {
        assert value != null;
        assert value.equals(value.toLowerCase());

        return allCommandWords.contains(value);
    }

    /**
     * Precondition: key is a new unique alias and not a command keyword;
     * value is a reserved command keyword.
     * Saves the new alias relationship (key can be used in place of value)
     */
    public void addNewAlias(String key, String value) {
        assert !isExistingAliasKey(key);
        assert !isReservedCommandKeyword(key);
        assert isReservedCommandKeyword(value);

        aliasTable.put(key, value);

        indicateAliasTableChanged(key + " aliased");
    }

    /**
     * Precondition: key is aliased to a command keyword.
     * Destroy the alias relationship (key can no longer be used in place of command keyword)
     */
    public void removeExistingAlias(String key) {
        assert isExistingAliasKey(key);

        aliasTable.remove(key);

        indicateAliasTableChanged(key + " unaliased");
    }

    /** Raises an event to indicate that the aliasTable in the command library has changed */
    private void indicateAliasTableChanged(String keyChanged) {
        EventsCenter eventCenter = EventsCenter.getInstance();
        eventCenter.post(new AliasTableChangedEvent(keyChanged, aliasTable));
    }

}
