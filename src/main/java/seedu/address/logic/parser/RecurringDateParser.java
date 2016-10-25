package seedu.address.logic.parser;

import java.util.HashSet;

import seedu.address.model.task.RecurringType;

//@@A0135782Y
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
        recurringTypes = new HashSet<RecurringType>();
        for(RecurringType t : RecurringType.values()) {
            recurringTypes.add(t);
        }
        recurringTypes.remove(RecurringType.IGNORED);
    }
    
    public RecurringType getRecurringType(String input) throws IllegalArgumentException {
        if (recurringTypes.contains(RecurringType.valueOf(input))) {
            return RecurringType.valueOf(input);
        }
        return RecurringType.IGNORED;
    }
    
    public static RecurringDateParser getInstance() {
        if (instance == null )
            instance = new RecurringDateParser();
        return instance;
    }
}
