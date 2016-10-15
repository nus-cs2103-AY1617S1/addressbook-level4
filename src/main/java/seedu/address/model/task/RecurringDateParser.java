package seedu.address.model.task;

import java.util.HashSet;

/**
 * Parses the recurring info to determine the recurring type of the input
 * 
 */
public class RecurringDateParser {
    private static RecurringDateParser instance;
    
    private HashSet<RecurringType> recurringTypes;
    
    private RecurringDateParser () {
        populateSupportedRecurringTypes();
    }

    private void populateSupportedRecurringTypes() {
        for(RecurringType t : RecurringType.values()) {
            recurringTypes.add(t);
        }
    }
    
    public RecurringType getRecurringType(String input) {
        if (RecurringType.valueOf(input) == null) {
            return RecurringType.NONE;
        }
        if (recurringTypes.contains(RecurringType.valueOf(input))) {
            return RecurringType.valueOf(input);
        }
        return RecurringType.NONE;
    }
    
    public static RecurringDateParser getInstance() {
        if (instance == null )
            instance = new RecurringDateParser();
        return instance;
    }
}
