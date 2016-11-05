package harmony.mastermind.logic.parser;

public class ParserMemoryMain { 
    
    protected static String command; 
    protected static String taskName; 
    protected static String description; 
    
    protected static int type;
    protected static int length; 
    
    protected static boolean containsDescription; 
    protected static boolean setProper;
    
    private static int day;
    private static int month; 
    private static int year;
    
    //@@author A0143378Y
    //General getters and setters
    public static String getCommand() { 
        return command;
    }
    
    //@@author A0143378Y
    public static void setCommand(String newCommand) { 
        command = newCommand;
    }
    
    //@@author A0143378Y
    public static void setTaskName(String newName) { 
        taskName = newName;
    }
    
    //@@author A0143378Y
    protected static void setDescription(String newDescription) { 
        description = newDescription;
    }
    
    //@@author A0143378Y 
    protected static void setLength(int newLength) { 
        length = newLength;
    }
    
    //@@author A0143378Y
    protected static void setType(int newType) {
        type = newType;
    }
}