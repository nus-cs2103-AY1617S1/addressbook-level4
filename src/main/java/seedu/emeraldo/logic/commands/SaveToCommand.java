package seedu.emeraldo.logic.commands;

public class SaveToCommand extends Command{
    
    public static final String COMMAND_WORD = "saveto";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + "TO-DO";
    
    public static final String MESSAGE_SUCCESS = "Save location changed";
    
    private String filepath;
    
    public SaveToCommand(String filepath){
        this.filepath = filepath;
    }
    
    public CommandResult execute(){
        /*
        TO-DO:
            change filepath in XmlEmeraldoStorage
            change filepath in Config.java
        */
        
        
        
        return new CommandResult("Stub");
    }
}
