package seedu.address.model.item;

//@@author A0139498J
public enum Priority {
    HIGH,
    MEDIUM,
    LOW;
    
    /** Name of key in map that maps to the priority of task */
    private static final String MAP_PRIORITY_KEY = "priority";
    
    /**
     * Converts given String into Priority
     */
    public static Priority convertStringToPriority(String priorityString) {
        assert priorityString != null;
        
        switch (priorityString) {
        case ("low"):
        case ("l"):
            return Priority.LOW;
        case ("high"):
        case ("h"):
            return Priority.HIGH;
        case ("medium"):
        case ("med"):
        case ("m"):
        default:
            return Priority.MEDIUM;
        }
    }
    
    /** 
     * @return the key in map that maps to the priority of task
     */
    public static String getMapPriorityKey() {
        return MAP_PRIORITY_KEY;
    }
}
