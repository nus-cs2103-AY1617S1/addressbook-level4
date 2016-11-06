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
    private static CommandLibrary commandLibrary = new CommandLibrary();

    private List<String> allCommandWords = new ArrayList<String>();

    // The keys of the hash table are user-defined aliases
    // The values of the has table are Agendum's reserved command keywords
    private Hashtable<String, String> aliasTable = new Hashtable<String, String>();

   

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

    //@author

    public static CommandLibrary getInstance() {
        return commandLibrary;
    }

    public Hashtable<String, String> getAliasTable() {
        return aliasTable;
    }

    //@@author A0133367E
    /**
     * Replace the current commandLibrary's aliasTable with the new aliasTable provided
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
     * Returns the reserved command keyword that is aliased by key
     * 
     * @param key    An existing user-defined alias for a reserved command keyword
     * @return       The associated reserved command keyword
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
     * Pre-condition: key is a new unique alias and not a command keyword;
     * value is a reserved command keyword.
     * Saves the new alias relationship between key and value.
     * 
     * @param key       A valid and unique user-defined alias for a reserved command word
     * @param value     The target reserved command word
     */
    public void addNewAlias(String key, String value) {
        assert !isExistingAliasKey(key);
        assert !isReservedCommandKeyword(key);
        assert isReservedCommandKeyword(value);

        aliasTable.put(key, value);

        indicateAliasAdded(key, value);
    }

    /**
     * Destroy the alias relationship (key can no longer be used in place of command word)
     * 
     * @param key    An existing user-defined alias for a reserved command word
     */
    public void removeExistingAlias(String key) {
        assert isExistingAliasKey(key);

        String value = aliasTable.remove(key);

        indicateAliasRemoved(key, value);
    }

    /**
     * Raises an event to indicate that an alias has been added to aliasTable in the command library
     * 
     * @param key    The new user-defined alias key
     * @param value  The target reserved command word
     */
    private void indicateAliasAdded(String key, String value) {
        String message = "Added alias " + key + " for " + value;
        EventsCenter eventCenter = EventsCenter.getInstance();
        eventCenter.post(new AliasTableChangedEvent(message, aliasTable));
    }

    /**
     * Raises an event to indicate that an alias has been removed from aliasTable in the command library
     * 
     * @param key       The alias key to be removed
     * @param value     The associated reserved command word
     */
    private void indicateAliasRemoved(String key, String value) {
        String message = "Removed alias " + key + " for " + value;
        EventsCenter eventCenter = EventsCenter.getInstance();
        eventCenter.post(new AliasTableChangedEvent(message, aliasTable));
    }

}
