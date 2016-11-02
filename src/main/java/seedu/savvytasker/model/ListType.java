//@@author A0139916U
package seedu.savvytasker.model;

/**
 * This enum represents what and how to list tasks/symbols.
 */
public enum ListType {
    /**
     * List tasks by due date.
     */
    DueDate,
    
    /**
     * List tasks by priority level.
     */
    PriorityLevel,
    
    /**
     * List archived tasks.
     */
    Archived,
    
    /**
     * List aliases.
     */
    Alias;
    
    /**
     * Gets a ListType enum object from its enum name, ignoring cases.
     * @param name the name of the ListType enum object
     * @return the corresponding ListType enum object
     */
    public static ListType valueOfIgnoreCase(String name) {
        for (ListType type : ListType.values()) {
            if (type.toString().equalsIgnoreCase(name))
                return type;
        }
        
        throw new IllegalArgumentException("Unknown list type: " + name);
    }
}
