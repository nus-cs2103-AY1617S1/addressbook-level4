package seedu.savvytasker.model.task;

/**
 * This enum represents the type of matching used when trying to find tasks 
 * from keywords.
 */
public enum FindType {
    /**
     * Specifies partial matching of a single keyword.
     * E.g. 'ap' matches 'happy'
     */
    Partial,
    /**
     * Specifies full matching of a single keyword.
     * E.g. 'ap' does not match 'happy', 'happy' matches 'very happy'
     */
    Full,
    /**
     * Specifies full matching of all keywords in a set of keywords.
     * E.g. 'happy' does not match 'very happy', 'very happy' matches 'very happy'
     */
    Exact;
    
    /**
     * Gets a FindType enum object from its enum name, ignoring cases.
     * @param name the name of the FindType enum object
     * @return the corresponding FindType enum object
     */
    public static FindType valueOfIgnoreCase(String name) {
        for (FindType type : FindType.values()) {
            if (type.toString().equalsIgnoreCase(name))
                return type;
        }
        
        throw new IllegalArgumentException("Unknown find type: " + name);
    }
}
